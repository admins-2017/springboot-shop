package com.zhike.api.v1;

import com.zhike.core.interceptors.ScopeLevel;
import com.zhike.service.PaymentNotifyService;
import com.zhike.service.WxPaymentService;
import com.zhike.util.WxNotify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Positive;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@Validated
public class PaymentController {

    @Autowired
    private WxPaymentService wxPaymentService;
    @Autowired
    private PaymentNotifyService paymentNotifyService;

    @ScopeLevel
    @PostMapping("/pay/order/{id}")
    public Map<String,String> preWxOrder(@PathVariable("id") @Positive Long orderId){
        return wxPaymentService.preOrder(orderId);
    }

    /**
     * 微信支付回调方法 获取微信支付结果
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/wx/notify")
    public String payCallback(HttpServletRequest request,
                              HttpServletResponse response) {
        InputStream s;
        try {
            s = request.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
//            返回到小程序未成功支付
            return WxNotify.fail();
        }
        String xml;
        xml = WxNotify.readNotify(s);
        try{
            this.paymentNotifyService.processPayNotify(xml);
        }
        catch (Exception e){
//            返回到小程序未成功支付
            return WxNotify.fail();
        }
//            返回成功支付信息
        return WxNotify.success();
    }
}
