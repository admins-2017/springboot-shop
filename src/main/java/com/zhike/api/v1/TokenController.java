package com.zhike.api.v1;

import com.zhike.dto.TokenDTO;
import com.zhike.dto.TokenGetDTO;
import com.zhike.exception.HttpException.NotFoundException;
import com.zhike.service.WxAuthenticationService;
import com.zhike.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/token")
public class TokenController {

    private final WxAuthenticationService wxAuthenticationService;

    public TokenController(WxAuthenticationService wxAuthenticationService) {
        this.wxAuthenticationService = wxAuthenticationService;
    }

    /**
     * 执行微信小程序登录
     * 1.用户code码换取openid
     * 2.判断用户是否为第一次登录 第一次登录注册用户/第二次登录不需要注册
     * 3. 获取用户 uid
     * 4. uid写入到jwt
     * 5. 返回jwt到小程序
     */
    @PostMapping
    public Map<String,String> getToken(@RequestBody @Validated TokenGetDTO userData){
        Map<String,String> map = new HashMap<>();
        String token = null;
        switch (userData.getType()){
            case USER_WX:
                token = wxAuthenticationService.code2Session(userData.getAccount());
                break;
            case USER_EMAIL:
                break;
            case USER_PHONE:
                break;
            default:
                throw new NotFoundException(10003);
        }
        map.put("token",token);
        return map;
    }

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    @PostMapping("/verify")
    public Map<String, Boolean> verify(@RequestBody TokenDTO token) {
        Map<String, Boolean> map = new HashMap<>();
        Boolean valid = JwtToken.verifyToken(token.getToken());
        map.put("is_valid", valid);
        return map;
    }
}
