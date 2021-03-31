package com.zhike.service;

import com.zhike.bo.OrderMessageBo;

/**
 * @author Administrator
 */
public interface CouponCancelService {

    /**
     * 订单超时 退还用户优惠券
     * @param bo redis返回的订单信息
     */
    void returnBack(OrderMessageBo bo);
}
