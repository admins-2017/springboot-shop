package com.zhike.service.impl;

import com.zhike.model.User;
import com.zhike.repository.UserRepository;
import com.zhike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findFirstById(id);
    }
}
