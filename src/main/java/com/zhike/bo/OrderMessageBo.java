package com.zhike.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
public class OrderMessageBo {

    /**
     * 订单id
     */
    private Long orderId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 优惠券id
     */
    private Long couponId;

    /**
     * redis 过期的key name
     */
    private String message;

    public OrderMessageBo(String message) {
        this.message = message;
        this.parseId(message);
    }

    /**
     * 将key name 装换成属性的值
     */
    private void parseId(String message){
        String[] temp = message.split("-");
        this.orderId = Long.valueOf(temp[0]);
        this.userId = Long.valueOf(temp[1]);
        this.couponId = Long.valueOf(temp[2]);
    }
}
