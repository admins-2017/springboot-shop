package com.zhike.service;

import com.github.wxpay.sdk.WXPay;

import java.util.Map;

public interface WxPaymentService {

    Map<String,String> preOrder(Long orderId);

    WXPay assembleWxPayConfig();
}
