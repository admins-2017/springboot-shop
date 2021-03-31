package com.zhike.service.impl;

import com.zhike.bo.OrderMessageBo;
import com.zhike.exception.HttpException.ServerErrorException;
import com.zhike.model.Order;
import com.zhike.repository.OrderRepository;
import com.zhike.repository.SkuRepository;
import com.zhike.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class OrderCancelServiceImpl implements OrderCancelService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private SkuRepository skuRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(OrderMessageBo messageBo) {

        if (messageBo.getOrderId()<=0){
            throw new ServerErrorException(9999);
        }
        this.cancelOrder(messageBo.getOrderId());
    }

    private void cancelOrder(Long orderId){
//        获取订单信息
        Optional<Order> orderOptional = orderRepository.findById(orderId);
//       获取订单信息 如果为空抛出异常
        Order order = orderOptional.orElseThrow(() ->
                new ServerErrorException(9999));
//        修改订单状态 res更新条数
        int res = orderRepository.cancelOrder(orderId);
        if (res != 1) {
            return;
        }
//        获取订单的sku的信息 执行归还库存到sku
        order.getSnapItems().forEach(i -> {
            skuRepository.recoverStock(i.getId(), i.getCount().longValue());
        });
    }
}
