package com.zhike.service;

import com.zhike.dto.OrderDTO;
import com.zhike.logic.OrderChecker;

/**
 * @author Administrator
 */
public interface OrderService {

    /**
     * 检验订单
     * @return
     */
    OrderChecker isOk(Long uid, OrderDTO dto);
}
