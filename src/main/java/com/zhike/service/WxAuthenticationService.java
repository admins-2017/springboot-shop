package com.zhike.service;

/**
 * 微信登录验证
 */
public interface WxAuthenticationService {

    String code2Session(String code);
}
