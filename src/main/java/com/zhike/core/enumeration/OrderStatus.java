package com.zhike.core.enumeration;

import java.util.stream.Stream;

/**
 * @author Administrator
 * 订单状态 枚举
 */

public enum OrderStatus {
    /**
     * 订单所有状态
     */
    All(0, "全部"),
    /**
     * 预扣库存 但未支付
     */
    UNPAID(1, "待支付"),
    /**
     * 已支付 并扣除库存
     */
    PAID(2, "已支付"),
    /**
     *
     */
    DELIVERED(3, "已发货"),
    /**
     *
     */
    FINISHED(4, "已完成"),
    /**
     * 订单超时或用户主动提交取消 归还库存
     */
    CANCELED(5, "已取消");
//
//    // 预扣除库存不存在以下这两种情况
//    PAID_BUT_OUT_OF(21, "已支付，但无货或库存不足"),
//    DEAL_OUT_OF(22, "已处理缺货但支付的情况");

    private int value;

    OrderStatus(int value, String text) {
        this.value = value;
    }

    public int value() {
        return this.value;
    }

    public static OrderStatus toType(int value) {
        return Stream.of(OrderStatus.values())
                .filter(c -> c.value == value)
                .findAny()
                .orElse(null);
    }
}