package com.zhike.service.impl;

import com.zhike.core.enumeration.CouponStatus;
import com.zhike.exception.HttpException.NotFoundException;
import com.zhike.exception.HttpException.ParameterException;
import com.zhike.model.Activity;
import com.zhike.model.Coupon;
import com.zhike.model.UserCoupon;
import com.zhike.repository.ActivityRepository;
import com.zhike.repository.CouponRepository;
import com.zhike.repository.UserCouponRepository;
import com.zhike.service.CouponService;
import com.zhike.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.ParameterExpression;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final ActivityRepository activityRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponServiceImpl(CouponRepository couponRepository, ActivityRepository activityRepository, UserCouponRepository userCouponRepository) {
        this.couponRepository = couponRepository;
        this.activityRepository = activityRepository;
        this.userCouponRepository = userCouponRepository;
    }

    @Override
    public List<Coupon> getCouponsByCategory(Long cid) {
        Date now = new Date();
        return couponRepository.findByCategory(cid,now);
    }

    @Override
    public List<Coupon> getWholeStoreCoupons() {
        Date now = new Date();
        return couponRepository.findByWholeStore(true,now);
    }

    @Override
    public void collectCoupon(Long uid, Long id) {
//        判断当前优惠券id是否存在 如果不存在就抛出异常
        this.couponRepository.findById(id).orElseThrow(() -> new NotFoundException(40003));
//      判断当前优惠券是否可以使用 有无过期或活动是否到期
        Activity activity = this.activityRepository.findByCouponListId(id).orElseThrow(() -> new NotFoundException(40010));
//        判断活动是否过期
        Date date = new Date();
        Boolean inTimeLine = CommonUtil.isInTimeLine(date, activity.getStartTime(), activity.getEndTime());
        if(!inTimeLine){
            throw new ParameterException(40005);
        }
//        判断用户是否已经领取过该优惠券 如果存在就是被领取 抛出异常
        this.userCouponRepository
                .findFirstByUserIdAndCouponId(uid, id)
                .ifPresent((uc)-> {throw new ParameterException(40006);});

        UserCoupon userCoupon = UserCoupon.builder().userId(uid).couponId(id).createTime(date).status(CouponStatus.AVAILABLE.getValue()).build();

        this.userCouponRepository.save(userCoupon);
    }

    @Override
    public List<Coupon> getMyAvailableCoupons(Long uid) {
        Date now = new Date();
        return couponRepository.findMyAvailable(uid,now);
    }

    @Override
    public List<Coupon> getMyUsedCoupons(Long uid) {
        Date now = new Date();
        return couponRepository.findMyUsed(uid,now);
    }

    @Override
    public List<Coupon> getMyExpiredCoupons(Long uid) {
        Date now = new Date();
        return couponRepository.findMyExpired(uid,now);
    }
}
