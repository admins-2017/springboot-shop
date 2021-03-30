package com.zhike.repository;

import com.zhike.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Optional;

/**
 * @author Administrator
 */
public interface UserCouponRepository extends JpaRepository<UserCoupon,Long> {

    /**
     * 判断用户是否已经领取了优惠券
     * @param uid 用户id
     * @param couponId 优惠券id
     * @param status 状态
     * @return 结果
     */
    Optional<UserCoupon> findFirstByUserIdAndCouponIdAndStatusAndOrderIdNull(Long uid , Long couponId,Integer status);

    /**
     * 获取用户优惠券
     * @param uid yonghuid
     * @param couponId 优惠券id
     * @return 优惠券集合
     */
    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid , Long couponId);
    /**
     * 核销优惠券
     * @param couponId 优惠券id
     * @param orderId 订单id
     * @param uid 用户id
     * @return 更新记录数
     */
    @Modifying
    @Query("update UserCoupon u set u.status = 2 ,u.updateTime = :now , u.orderId = :orderId \n" +
            "where u.userId =:uid and u.couponId = :couponId and u.status = 1 and u.orderId is null")
    int writeOff(Long couponId, Long orderId, Long uid, Date now);

}
