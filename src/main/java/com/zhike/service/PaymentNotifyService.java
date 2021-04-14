package com.zhike.service;

/**
 * @author Administrator
 */
public interface PaymentNotifyService {

    /**
     * 支付方法
     * @param data 传递数据
     */
    void processPayNotify(String data);
}
