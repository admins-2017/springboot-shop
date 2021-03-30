package com.zhike.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayUtil;
import com.zhike.core.enumeration.OrderStatus;
import com.zhike.exception.HttpException.ServerErrorException;
import com.zhike.model.Order;
import com.zhike.repository.OrderRepository;
import com.zhike.service.PaymentNotifyService;
import com.zhike.service.WxPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class PaymentNotifyServiceImpl implements PaymentNotifyService {

    private final WxPaymentService wxPaymentService;

    private final OrderRepository orderRepository;

    public PaymentNotifyServiceImpl(WxPaymentService wxPaymentService,OrderRepository orderRepository) {
        this.wxPaymentService = wxPaymentService;
        this.orderRepository = orderRepository;
    }

    @Transactional
    public void processPayNotify(String data) {
        Map<String, String> dataMap;
        try {
//            将xml转换成map
            dataMap = WXPayUtil.xmlToMap(data);
        } catch (Exception e) {
            e.printStackTrace();
//            转换失败抛出异常
            throw new ServerErrorException(9999);
        }
//        获取微信请求配置
        WXPay wxPay = this.wxPaymentService.assembleWxPayConfig();
        boolean valid;
        try {
//          校验返回结果  判断xml数据的sign是否有效
            valid = wxPay.isResponseSignatureValid(dataMap);
        } catch (Exception e) {
            e.printStackTrace();
//            失败抛出异常
            throw new ServerErrorException(9999);

        }
        if (!valid) {
            throw new ServerErrorException(9999);
        }
//      获取返回的code码
        String returnCode = dataMap.get("return_code");
//      获取返回的订单编号
        String orderNo = dataMap.get("out_trade_no");
//      获取返回的code码
        String resultCode = dataMap.get("result_code");

        if (!returnCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (!resultCode.equals("SUCCESS")) {
            throw new ServerErrorException(9999);
        }
        if (orderNo == null) {
            throw new ServerErrorException(9999);
        }
        this.deal(orderNo);
    }

    private void deal(String orderNo) {
//        根据订单号获取订单信息
        Optional<Order> orderOptional = this.orderRepository.findFirstByOrderNo(orderNo);
//        订单不存在抛出异常
        Order order = orderOptional.orElseThrow(() -> new ServerErrorException(9999));

        int res = -1;
//      如果为待支付状态或已取消 将订单状态改为已支付
        if (order.getStatus().equals(OrderStatus.UNPAID.value())
                || order.getStatus().equals(OrderStatus.CANCELED.value())) {
            res = this.orderRepository.updateStatusByOrderNo(orderNo, OrderStatus.PAID.value());
        }
//       如果更新条数不为1 则表示没有订单被更新 抛出异常
        if (res != 1) {
            throw new ServerErrorException(9999);
        }
    }
}
