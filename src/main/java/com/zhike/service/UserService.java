package com.zhike.service;

import com.zhike.model.User;

/**
 * @author Administrator
 */
public interface UserService {

    /**
     * 根据id获取用户
     * @param id
     * @return
     */
    User getUserById(Long id);
}
