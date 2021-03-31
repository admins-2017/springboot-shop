package com.zhike.service;

import com.zhike.bo.OrderMessageBo;

/**
 * @author Administrator
 * 订单取消服务
 */
public interface OrderCancelService {

    /**
     * 订单超时 将退还库存
     * @param messageBo
     */
    void cancel(OrderMessageBo messageBo);
}
