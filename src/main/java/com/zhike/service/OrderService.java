package com.zhike.service;

import com.zhike.dto.OrderDTO;
import com.zhike.logic.OrderChecker;
import com.zhike.model.Order;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
public interface OrderService {

    /**
     * 检验订单
     * @return
     */
    OrderChecker isOk(Long uid, OrderDTO dto);

    /**
     * 下单
     * @param orderChecker 订单校验
     * @param orderDTO 前端传递的订单值
     * @param uid 用户id
     * @return 订单id
     *
     */
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker);

    /**
     * 获取分页未支付订单
     * @param page 页码
     * @param size 条数
     * @return 订单集合
     */
     Page<Order> getUnpaid(Integer page, Integer size);

    /**
     * 根据状态获取订单
     * @param status 状态值
     * @param page 页码
     * @param size 条数
     * @return 订单集合
     */
    Page<Order> getOrderByStatus(Integer status,Integer page, Integer size);

    /**
     * 获取订单详情
     * @param oid 订单id
     * @return 订单详情
     */
    Optional<Order> getOrderDetail(Long oid);

    /**
     * 更新订单的微信单号id
     * @param orderId 订单id
     * @param prePayId 微信订单id
     */
    void updateOrderPrepayId(Long orderId, String prePayId);
}
