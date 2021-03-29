package com.zhike.repository;

import com.zhike.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Administrator
 */
public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    /**
     * 判断用户是否已经领取了优惠券
     * @param uid 用户id
     * @param couponId 优惠券id
     * @return 结果
     */
    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid , Long couponId);

}
