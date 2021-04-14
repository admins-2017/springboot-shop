package com.zhike.service.impl;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConstants;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.ZhiKePayConfig;
import com.zhike.core.LocalUser;
import com.zhike.exception.httpexception.ForbiddenException;
import com.zhike.exception.httpexception.NotFoundException;
import com.zhike.exception.httpexception.ParameterException;
import com.zhike.exception.httpexception.ServerErrorException;
import com.zhike.model.Order;
import com.zhike.repository.OrderRepository;
import com.zhike.service.OrderService;
import com.zhike.service.WxPaymentService;
import com.zhike.util.CommonUtil;
import com.zhike.util.HttpRequestProxy;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class WxPaymentServiceImpl implements WxPaymentService {

    @Value("${shop.order.pay-callback-host}")
    private String payCallbackHost;

    @Value("${shop.order.pay-callback-path}")
    private String payCallbackPath;

    private final OrderRepository orderRepository;
    private final OrderService orderService;


    private static final ZhiKePayConfig zhiKePayConfig = new ZhiKePayConfig();

    public WxPaymentServiceImpl(OrderRepository orderRepository, OrderService orderService) {
        this.orderRepository = orderRepository;
        this.orderService =orderService;
    }

    @Override
    public Map<String, String> preOrder(Long orderId) {
        Long userId = LocalUser.getUser().getId();
        Optional<Order> orderOptional = orderRepository.findFirstByUserIdAndId(userId, orderId);
//        判断前端返回的订单id是否存在
        Order order = orderOptional.orElseThrow(() ->
             new NotFoundException(50009)
        );
//        判断订单是否过期 过期则被取消掉
        if(order.needCancel()){
            throw new ForbiddenException(50010);
        }

//        获取微信支付api 发送请求类
        WXPay wxPay = this.assembleWxPayConfig();
//        封装向微信请求的参数
        Map<String, String> map = this.makePreOrderParams(order.getFinalTotalPrice(), order.getOrderNo());
//      获取微信订单信息
        Map<String, String> wxOrder;
//        统一向微信服务器下单
        try {
            wxOrder = wxPay.unifiedOrder(map);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
//        判断请求是否成功 如果不成功抛出异常
        if(!wxOrder.get("return_code").equals("SUCCESS") || !wxOrder.get("result_code").equals("SUCCESS")){
            throw new ParameterException(10007);
        }

//        prepay_id 微信订单的id号
//        更新prepay_id 到数据库订单中 防止用户多次点击支付重复向微信发送订单请求
        orderService.updateOrderPrepayId(order.getId(),wxOrder.get("prepay_id"));
//        orderService.updateOrderPrepayId(order.getId(),"123456");

//        生成数据返回到小程序 使用wx.requestPayMent 唤起微信支付
        Map<String, String> stringStringMap = this.makePaySignature(wxOrder);
        return stringStringMap;
    }

    /**
     * 生成数据返回微信小程序 唤起微信支付
     * @param wxOrder 微信订单
     * @return
     */
    private Map<String, String> makePaySignature(Map<String, String> wxOrder) {
        Map<String, String> wxPayMap = new HashMap<>(10);
//        微信订单id
        String packages = "prepay_id=" + wxOrder.get("prepay_id");
//      获取appid
        wxPayMap.put("appId",WxPaymentServiceImpl.zhiKePayConfig.getAppID());
//      10位的时间戳
        wxPayMap.put("timeStamp", CommonUtil.timestamp10());
//        生成随机字符串
        wxPayMap.put("nonceStr", RandomStringUtils.randomAlphanumeric(32));
//        订单id
        wxPayMap.put("package", packages);
//        签名格式
        wxPayMap.put("signType", "HMAC-SHA256");
//      生成签名
        String sign;
        try {
            sign = WXPayUtil.generateSignature(wxPayMap, WxPaymentServiceImpl.zhiKePayConfig.getKey(), WXPayConstants.SignType.HMACSHA256);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
//      将签名放入返回小程序的map中
        Map<String, String> miniPayParams = new HashMap<>(10);
        miniPayParams.put("paySign", sign);
        miniPayParams.putAll(wxPayMap);
//        移除appid
        miniPayParams.remove("appId");
        return miniPayParams;
    }

    /**
     * 配置微信请求带的参数
     * @param serverFinalPrice
     * @param orderNo
     * @return
     */
    private Map<String, String> makePreOrderParams(BigDecimal serverFinalPrice, String orderNo) {
        String payCallbackUrl = this.payCallbackHost + this.payCallbackPath;
        Map<String, String> data = new HashMap<>(10);
//        商品的标题
        data.put("body", "智客科技");
//        订单编号
        data.put("out_trade_no", orderNo);
//
        data.put("device_info", "智客科技");
//        货币单位
        data.put("fee_type", "CNY");
//        小程序api固定参数
        data.put("trade_type", "JSAPI");
//         支付的订单金额 按分结算
        data.put("total_fee", CommonUtil.yuanToFenPlainString(serverFinalPrice));
//        获取用户的openid
        data.put("openid", LocalUser.getUser().getOpenid());
//        当前用户的手机的ip地址
        data.put("spbill_create_ip", HttpRequestProxy.getRemoteRealIp());
//      接受微信支付结果的 api地址  微信支付回调接口
        data.put("notify_url", payCallbackUrl);
        return data;
    }

    /**
     * 配置微信请求
     * @return
     */
    @Override
    public WXPay assembleWxPayConfig(){
        WXPay wxPay;
        try{
            wxPay = new WXPay(WxPaymentServiceImpl.zhiKePayConfig);
        }catch (Exception e){
            throw new ServerErrorException(9999);
        }
        return wxPay;
    }
}
