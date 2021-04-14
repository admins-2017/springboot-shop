package com.zhike.service.impl;

import com.zhike.bo.OrderMessageBo;
import com.zhike.core.enumeration.OrderStatus;
import com.zhike.exception.httpexception.ServerErrorException;
import com.zhike.model.Order;
import com.zhike.repository.OrderRepository;
import com.zhike.repository.UserCouponRepository;
import com.zhike.service.CouponCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class CouponCancelServiceImpl implements CouponCancelService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void returnBack(OrderMessageBo bo) {
        Long couponId = bo.getCouponId();
        Long uid = bo.getUserId();
        Long orderId = bo.getOrderId();
//      如果优惠券id为-1 表示用户此次订单未使用优惠券 不需要退还
        if (couponId == -1) {
            return;
        }
//      获取订单信息 如果订单为空抛出异常
        Optional<Order> optional = orderRepository.findFirstByUserIdAndId(uid, orderId);
        Order order = optional.orElseThrow(() ->new ServerErrorException(9999));
//      判断订单状态是否为 未支付 或 已取消状态 如果不是说明订单不需要退还
        if (order.getStatusEnum().equals(OrderStatus.UNPAID)
                || order.getStatusEnum().equals(OrderStatus.CANCELED)) {
            this.userCouponRepository.returnBack(couponId, uid);
        }
    }
}
