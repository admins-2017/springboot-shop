package com.zhike.manager.redis;

import com.zhike.bo.OrderMessageBo;
import com.zhike.service.CouponCancelService;
import com.zhike.service.OrderCancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 监听reids key过期后回调事件 __keyevent@7__:expired
 * 如果key过期 则订阅的事件返回key name
 *
 */
@Component
public class TopicMessageListener implements MessageListener {

    @Autowired
    private OrderCancelService orderCancelService;

    @Autowired
    private CouponCancelService couponCancelService;

    /**
     * 获取广播事件名称
     *         byte[] channel = message.getChannel();
     *         String topic = new String(channel);
     * 获取返回key的 name 值
     *          String expiredKey = new String(body);
     * @param message 消息体
     * @param bytes bytes
     */
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
//      key的 name 值
        String expiredKey = new String(body);

        OrderMessageBo messageBo = new OrderMessageBo(expiredKey);
        System.out.println(messageBo.toString());
//        orderCancelService.cancel(messageBo);
//
//        couponCancelService.returnBack(messageBo);
    }
}