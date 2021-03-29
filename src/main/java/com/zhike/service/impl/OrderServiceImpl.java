package com.zhike.service.impl;

import com.zhike.dto.OrderDTO;
import com.zhike.dto.SkuInfoDTO;
import com.zhike.exception.HttpException.NotFoundException;
import com.zhike.exception.HttpException.ParameterException;
import com.zhike.logic.CouponChecker;
import com.zhike.model.Coupon;
import com.zhike.model.Sku;
import com.zhike.model.UserCoupon;
import com.zhike.repository.CouponRepository;
import com.zhike.repository.UserCouponRepository;
import com.zhike.service.OrderService;
import com.zhike.service.SkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private SkuService skuService;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private UserCouponRepository userCouponRepository;

    /**
     * 订单校验
     * 1. 商品库存
     * 2. 校验最大购买数量
     * 3. 校验原始价格 totalPrice
     * 4. 校验折扣后的最终价格
     * 5. 校验用户是否有使用优惠券，用户是否有优惠券
     * 6. 优惠券是否过期
     *
     * OrderChecker 订单校验类
     * CouponChecker 优惠券校验类
     */
    @Override
    public Boolean isOk(Long uid, OrderDTO dto) {
//       检验价格是否小于等于0
        if (dto.getTotalPrice().compareTo(new BigDecimal("0")) <= 0){
            throw new ParameterException(50011);
        }
//        获取商品 sku id数组
        List<Long> skuId = dto.getSkuInfoList().stream().map(SkuInfoDTO::getId).collect(Collectors.toList());
//         根据商品id获取商品信息
        List<Sku> skuList = skuService.getSkuListByIds(skuId);
//        获取优惠券id
        Long couponId = dto.getCouponId();
//        如果优惠券为空 表示没有使用优惠券 则不需要对优惠券进行校验
        CouponChecker couponChecker = null;
        if (couponId!=null){
//            获取到优惠券 如果为空表示优惠券根本不存在  抛出异常
            Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new NotFoundException(40004));
//            获取到用户的优惠券 如果为空表示用户根本就没有这张优惠券 抛出异常
            UserCoupon userCoupon = userCouponRepository.findFirstByUserIdAndCouponId(uid, couponId).orElseThrow(() -> new NotFoundException(50006));

             couponChecker = new CouponChecker(coupon,userCoupon);
        }

        return null;
    }
}
