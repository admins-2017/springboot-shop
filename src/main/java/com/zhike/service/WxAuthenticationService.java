package com.zhike.service;

/**
 * 微信登录验证
 * @author Administrator
 */
public interface WxAuthenticationService {

    /**
     * 请求微信服务器 用户code换取openid
     * @param code 用户code码
     * @return 微信服务器相应数据
     */
    String code2Session(String code);
}
