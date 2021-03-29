package com.zhike.service;

import com.zhike.dto.OrderDTO;

/**
 * @author Administrator
 */
public interface OrderService {

    /**
     * 检验订单
     * @return
     */
    Boolean isOk(Long uid, OrderDTO dto);
}
