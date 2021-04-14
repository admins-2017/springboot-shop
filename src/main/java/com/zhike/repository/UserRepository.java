package com.zhike.repository;

import com.zhike.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Administrator
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    /**
     * 根据邮箱获取用户
     * @param email 邮箱号
     * @return 用户
     */
    User findByEmail(String email);

    /**
     * 根据微信openid获取用户
     * @param openid 微信openID
     * @return 用户
     */
    Optional<User> findByOpenid(String openid);

    /**
     * 根据id获取用户
     * @param id 用户id
     * @return 用户
     */
    User findFirstById(Long id);

    /**
     * 根据学号
     * @param uuid 学号
     * @return 用户
     */
    User findByUnifyUid(Long uuid);
}
