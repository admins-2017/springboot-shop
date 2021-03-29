package com.zhike.service;

import com.zhike.model.Coupon;

import java.util.List;

/**
 * @author Administrator
 */
public interface CouponService {
    /**
     * 根据二级分类id获取优惠券
     * @param cid 分类id
     * @return
     */
    List<Coupon> getCouponsByCategory(Long cid);

    /**
     * 获取全场券
     * @return
     */
    List<Coupon> getWholeStoreCoupons();

    /**
     * 领取优惠券
     * @param uid 用户id
     * @param id 优惠券id
     */
    void collectCoupon(Long uid, Long id);

    /**
     * 获取未使用的优惠券
     * @param uid
     * @return
     */
    List<Coupon> getMyAvailableCoupons(Long uid);

    /**
     * 获取已使用的优惠券
     * @param uid
     * @return
     */
    List<Coupon> getMyUsedCoupons(Long uid);


    /**
     * 获取已过期的优惠券
     * @param uid
     * @return
     */
    List<Coupon> getMyExpiredCoupons(Long uid);
}
