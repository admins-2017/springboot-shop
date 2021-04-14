package com.zhike.service;

import com.github.wxpay.sdk.WXPay;

import java.util.Map;

/**
 * @author Administrator
 * 微信支付接口
 */
public interface WxPaymentService {

    /**
     * 生成订单
     * @param orderId 订单id
     * @return 订单结果
     */
    Map<String,String> preOrder(Long orderId);

    /**
     * 获取支付配置
     * @return 微信配置
     */
    WXPay assembleWxPayConfig();
}
