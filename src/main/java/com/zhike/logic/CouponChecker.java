package com.zhike.logic;

import com.zhike.bo.SkuOrderBO;
import com.zhike.core.enumeration.CouponType;
import com.zhike.core.money.IMoneyDiscount;
import com.zhike.exception.HttpException.ForbiddenException;
import com.zhike.exception.HttpException.ParameterException;
import com.zhike.model.Category;
import com.zhike.model.Coupon;
import com.zhike.model.UserCoupon;
import com.zhike.util.CommonUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * 优惠券校验类
 */
public class CouponChecker {

    private Coupon coupon;

    private IMoneyDiscount iMoneyDiscount;

    public CouponChecker(Coupon coupon , IMoneyDiscount iMoneyDiscount){
        this.coupon = coupon;
        this.iMoneyDiscount = iMoneyDiscount;
    }

    /**
     * 判断优惠券是否过期
     */
    public void isOk(){
//       判断是否过期
        Date now = new Date();
        Boolean isInTimeLine = CommonUtil.isInTimeLine(now,this.coupon.getStartTime(),this.coupon.getEndTime());
        if (!isInTimeLine){
            throw new ForbiddenException(40007);
        }
    }

    /**
     * 验证最终价格是否正确
     * @param orderTotalPrice 前端计算的总金额
     * @param serverTotalPrice 原价
     */
    public void finalTotalPrice(BigDecimal orderTotalPrice,BigDecimal serverTotalPrice){
        BigDecimal serverFinalTotalPrice;
        switch (CouponType.toType(this.coupon.getType())){
            case FULL_MINUS:
            case NO_THRESHOLD_MINUS:
//                满减
                serverFinalTotalPrice = serverTotalPrice.subtract(this.coupon.getMinus());
//                  判断 满减/无门槛减除券 最终价格不能小于0
                if (serverFinalTotalPrice.compareTo(new BigDecimal("0"))<= 0){
//                    如果小于0 则抛出异常
                    throw new ForbiddenException(50008);
                }
                break;
            case FULL_OFF:
                serverFinalTotalPrice = this.iMoneyDiscount.discount(serverTotalPrice,this.coupon.getRate());
                break;
            default:
                throw new ParameterException(40009);
        }
//                如果不等于0 说明前端的计算有误
        int compare = serverFinalTotalPrice.compareTo(orderTotalPrice);
        if (compare != 0){
            throw new ForbiddenException(50008);
        }
    }

    /**
     * 核对当前优惠券是否能被使用
     * 检验带有分类限制的优惠券是否能被订单使用
     * @param boList 商品及数量业务对象
     * @param serverTotalPrice 服务端传递的总价格
     */
    public void canBeUsed(List<SkuOrderBO> boList,BigDecimal serverTotalPrice ){
        BigDecimal orderCategoryPrice;
//        如果当前优惠券为全场券 则不需要计算分类是否够满减
        if (this.coupon.getWholeStore()){
            orderCategoryPrice = serverTotalPrice;
        }else{
//            获取当前优惠券适用的分类的id
            List<Long> cidList = this.coupon.getCategoryList().stream().map(Category::getId).collect(Collectors.toList());
            orderCategoryPrice = this.getSumByCategoryList(boList,cidList);
        }
        this.couponCanBeUsed(orderCategoryPrice);
    }

    /**
     * 判断是否达到使用优惠券的要求
     */
    private void couponCanBeUsed(BigDecimal orderCategoryPrice){
        switch (CouponType.toType(this.coupon.getType())){
            case FULL_OFF:
            case FULL_MINUS:
//                对满减券进行判断 如果大于0 说明未满足优惠券要求金额
                int compare = this.coupon.getFullMoney().compareTo(orderCategoryPrice);
                if(compare > 0){
                    throw new ParameterException(40008);
                }
                break;
            case NO_THRESHOLD_MINUS:
                break;
            default:
                throw  new ParameterException(40009);
        }
    }

    /**
     * 获取所有分类的总金额
     * @param list
     * @param cids
     * @return
     */
    private BigDecimal getSumByCategoryList(List<SkuOrderBO> list,List<Long> cids){
        BigDecimal sum = cids.stream().map(cid -> this.getSumByCategory(list,cid))
                .reduce(BigDecimal::add).orElse(new BigDecimal("0"));
        return sum;
    }

    /**
     * 根据分类获取分类的总金额
     * @param list 所有商品信息
     * @param cid 分类id
     * @return
     */
    private BigDecimal getSumByCategory(List<SkuOrderBO> list,Long cid){
        BigDecimal sum = list.stream()
//                过滤获取到 分类 id = cid的商品
                .filter(sku -> sku.getCategoryId().equals(cid))
//                获取到每一个商品的价格 map为商品价格列表
                .map(bo -> bo.getTotalPrice())
//                使用商品的价格列表的金额 进行累加金额 reduce将数组中的元素累加
                .reduce(BigDecimal::add).orElse(new BigDecimal("0"));
        return sum;
    }
}
