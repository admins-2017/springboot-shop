package com.zhike.core.interceptors;

import com.auth0.jwt.interfaces.Claim;
import com.zhike.exception.HttpException.ForbiddenException;
import com.zhike.exception.HttpException.UnAuthenticatedException;
import com.zhike.util.JwtToken;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @author kang
 * 权限拦截器
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    public PermissionInterceptor() {
        super();
    }

    /**
     * 请求进入controller之前
     * 1。获取请求中的令牌
     * 2。验证token
     * 3。判断路径是否需要权限
     * 4。如果需要权限scope，则需要读取 访问路径 @ScopeLevel中的等级
     * 5。判断当前用户的scope是否大于 @ScopeLevel中的等级
     * @param request
     * @param response
     * @param handler 请求的方法
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional<ScopeLevel> scopeLevel = this.getScopeLevel(handler);
//        如果scopeLevel为空 则表示方法上没有scopeLevel注解 方法为公开方法 则直接返回true 可以访问
        if (!scopeLevel.isPresent()){
            return true;
        }
//        如果scopeLevel不为空 说明需要权限验证 则需要验证token
//        获取请求头中的token
        String bearerToken = request.getHeader("Authorization");
//        判断token 是否为空
        if(StringUtils.isEmpty(bearerToken)){
            throw new UnAuthenticatedException(10004);
        }
//        判断token 是否以Bearer开头的
        if (!bearerToken.startsWith("Bearer")){
//        如果不是以Bearer开头 抛出异常
            throw new UnAuthenticatedException(10004);
        }
//        获取token bearerToken中的格式 例如 Bearer xxxxx... 中间以空格分割
        String[] tokens = bearerToken.split(" ");
        if(!(tokens.length==2)){
            throw new UnAuthenticatedException(10004);
        }
//        所以按空格分割 获取空格后面的token
        String token = tokens[1];
//        获取令牌中的数据
        Optional<Map<String, Claim>> claim = JwtToken.getClaim(token);
//        判断用户令牌是否验证通过 如果通过则赋值 如果不通过则抛出异常
        Map<String,Claim> map = claim
                .orElseThrow(() -> new UnAuthenticatedException(10004));
//        判断权限
        boolean valid = this.hasPermission(scopeLevel.get(),map);
        return valid;
    }

    /**
     * 判断用户是否有权限访问
     * @param scopeLevel 方法权限等级
     * @param map 用户令牌数据
     * @return
     */
    private boolean hasPermission(ScopeLevel scopeLevel, Map<String,Claim> map){
//        获取方法注解中的参数值（权限等级）
        Integer level = scopeLevel.value();
//        获取令牌中的权限等级
        Integer scope =map.get("scope").asInt();
//        判断用户权限等级是否大于方法规定的等级
        if( scope < level){
//            抛出权限不足
            throw new ForbiddenException(10005);
        }
        return true;
    }

    /**
     * 获取方法上的注解 @ScopeLevel
     * @param handler
     * @return
     */
    private Optional<ScopeLevel> getScopeLevel(Object handler){
//        判断当前方法是否为HandlerMethod
        if (handler instanceof HandlerMethod){
            HandlerMethod handlerMethod = (HandlerMethod) handler;
//            获取方法上的ScopeLevel注解
            ScopeLevel scopeLevel = handlerMethod.getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel == null){
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
