package com.zhike.service;

import com.zhike.dto.TokenGetDTO;

/**
 * 普通登录验证服务类
 * @author Administrator
 */
public interface AuthenticationService {

    /**
     * 根据email登录获取token
     * @param dto
     */
    void getTokenByEmail(TokenGetDTO dto);

    /**
     * 注册用户 微信小程序不需要注册
     * @param dto
     */
    void register(TokenGetDTO dto);

    /**
     * 根据微信验证
     * @param dto
     */
    void validateByWX(TokenGetDTO dto);

}
