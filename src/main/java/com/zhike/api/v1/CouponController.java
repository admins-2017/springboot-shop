package com.zhike.api.v1;

import com.zhike.core.LocalUser;
import com.zhike.core.UnifyResponse;
import com.zhike.core.enumeration.CouponStatus;
import com.zhike.core.interceptors.ScopeLevel;
import com.zhike.exception.httpexception.ParameterException;
import com.zhike.model.Coupon;
import com.zhike.service.CouponService;
import com.zhike.vo.CouponCategoryVO;
import com.zhike.vo.CouponPureVO;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Administrator
 */
@RestController
@RequestMapping("/coupon")
public class CouponController {

    private final CouponService couponService;

    /**
     * 根据二级分类id获取优惠券
     * @param cid 分类id
     */
    @GetMapping("/by/category/{cid}")
    public List<CouponPureVO> getCouponsByCategoryId(@PathVariable Long cid){
        List<Coupon> couponList = couponService.getCouponsByCategory(cid);
        if (couponList.isEmpty()){
            return Collections.emptyList();
        }
        return CouponPureVO.getList(couponList);
    }

    /**
     * 获取全场券
     * @return 优惠券
     */
    @GetMapping("/whole_store")
    public List<CouponPureVO> getWholeStoreCouponList() {
        List<Coupon> coupons = this.couponService.getWholeStoreCoupons();
        if (coupons.isEmpty()) {
            return Collections.emptyList();
        }
        return CouponPureVO.getList(coupons);
    }

    /**
     * 领取优惠券
     */
    @ScopeLevel
    @PostMapping("/collect/{id}")
    public void collectCoupon(@PathVariable Long id){
//        获取用户id
        Long uid = LocalUser.getUser().getId();
        couponService.collectCoupon(uid,id);
        UnifyResponse.createSuccess(0);
    }

    /**
     * 获取我的优惠券
     * @param status
     * @return
     */
    @ScopeLevel
    @GetMapping("/myself/by/status/{status}")
    public List<CouponPureVO> getMyCouponsByStatus(@PathVariable Integer status){
//        获取用户id
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList;
        switch (CouponStatus.toType(status)){
            case AVAILABLE:
                couponList = couponService.getMyAvailableCoupons(uid);
                break;
            case USED:
                couponList = couponService.getMyUsedCoupons(uid);
                break;
            case EXPIRED:
                couponList = couponService.getMyExpiredCoupons(uid);
                break;
            default:
                throw new ParameterException(40001);
        }
        return CouponPureVO.getList(couponList);
    }

    /**
     * 获取用户下的优惠券及分类
     * @return
     */
    @ScopeLevel
    @GetMapping("/myself/available/with_category")
    public List<CouponCategoryVO> getUserCouponWithCategory(){
        Long uid = LocalUser.getUser().getId();
        List<Coupon> couponList = couponService.getMyAvailableCoupons(uid);
        if (couponList.isEmpty()){
            return Collections.emptyList();
        }
        List<CouponCategoryVO> collect = couponList.stream().map(coupon -> {
            CouponCategoryVO vo = new CouponCategoryVO(coupon);
            return vo;
        }).collect(Collectors.toList());

        return collect;
    }

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }
}
