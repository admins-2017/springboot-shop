package com.zhike.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author kang
 * JWT 令牌
 */
@Component
public class JwtToken {
    /**
     * 设置密钥
     */
    private static String jwtKey;
    /**
     * 过期时间
     */
    private static Integer expiredTimeIn;
    /**
     * 默认等级
     */
    private static final Integer DEFAULTSCOPE  = 8;

    @Value("${shop.security.jwt-key}")
    public void setJwtKey(String jwtKey) {
        JwtToken.jwtKey = jwtKey;
    }

    @Value("${shop.security.token-expired-in}")
    public void setExpiredTimeIn(Integer expiredTimeIn) {
        JwtToken.expiredTimeIn = expiredTimeIn;
    }

    /**
     * 获取令牌
     * @param uid 用户id
     * @param scope 用户等级 涉及到权限 scope等级低的用户无法访问
     * @return 令牌
     */
    public static String makeToken(Long uid,Integer scope){
        return JwtToken.getToken(uid,scope);
    }

    /**
     * 获取默认等级的用户令牌
     * @param uid 用户id
     * @return 令牌
     */
    public static String makeToken(Long uid) {
        return JwtToken.getToken(uid, JwtToken.DEFAULTSCOPE);
    }

    public static String getToken(Long uid,Integer scope){
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
        Map<String, Date> stringDateMap = JwtToken.calculateExpiredIssues();
        /*
         * 生成令牌
         * sign 算法
         * withClaim 设置令牌参数
         * withExpiresAt 设置过期时间
         * withIssuedAt 设置签发时间
         */
        return JWT.create().withClaim("uid",uid)
                .withClaim("scope",scope)
                .withExpiresAt(stringDateMap.get("expiredTime"))
                .withIssuedAt(stringDateMap.get("now"))
                .sign(algorithm);
    }

    /**
     * 验证令牌是否有效并获取令牌中的数据
     * Claim 令牌中用户数据
     * @param token token字符串
     * @return map
     */
    public static Optional<Map<String, Claim>> getClaim(String token){
        DecodedJWT decodedJWT ;
//        设置算法
        Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
//        获取验证类
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        try {
//        验证token
            decodedJWT = jwtVerifier.verify(token);
        }catch (JWTVerificationException e){
//            如果验证token不通过
            return Optional.empty();
        }
//        获取用户数据
        return Optional.of(decodedJWT.getClaims());
    }

    /**
     * 获取时间
     */
    private static Map<String, Date> calculateExpiredIssues() {
        Map<String, Date> map = new HashMap<>(10);
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.SECOND, JwtToken.expiredTimeIn);
//        当前时间
        map.put("now", now);
//        过期时间
        map.put("expiredTime", calendar.getTime());
        return map;
    }

    /**
     * 验证token是否有效
     * @param token 令牌
     * @return 是否有效
     */
    public static Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(JwtToken.jwtKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
//            验证token 不通过抛出异常
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            return false;
        }
        return true;
    }
}
