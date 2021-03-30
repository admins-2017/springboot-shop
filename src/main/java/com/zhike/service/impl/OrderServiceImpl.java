package com.zhike.service.impl;

import com.zhike.core.LocalUser;
import com.zhike.core.enumeration.OrderStatus;
import com.zhike.core.money.IMoneyDiscount;
import com.zhike.dto.OrderDTO;
import com.zhike.dto.SkuInfoDTO;
import com.zhike.exception.HttpException.ForbiddenException;
import com.zhike.exception.HttpException.NotFoundException;
import com.zhike.exception.HttpException.ParameterException;
import com.zhike.logic.CouponChecker;
import com.zhike.logic.OrderChecker;
import com.zhike.model.*;
import com.zhike.repository.CouponRepository;
import com.zhike.repository.OrderRepository;
import com.zhike.repository.SkuRepository;
import com.zhike.repository.UserCouponRepository;
import com.zhike.service.OrderService;
import com.zhike.service.SkuService;
import com.zhike.util.CommonUtil;
import com.zhike.util.OrderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Service
public class OrderServiceImpl implements OrderService {

    private final SkuService skuService;

    private final CouponRepository couponRepository;

    private final UserCouponRepository userCouponRepository;

    private final IMoneyDiscount iMoneyDiscount;

    private final OrderRepository orderRepository;

    private final SkuRepository skuRepository;

    @Value("${shop.order.max-sku-limit}")
    private int maxSkuLimit;

    @Value("${shop.order.pay-time-limit}")
    private Integer payTimeLimit;

    public OrderServiceImpl(SkuService skuService, CouponRepository couponRepository, UserCouponRepository userCouponRepository, IMoneyDiscount iMoneyDiscount, OrderRepository orderRepository, SkuRepository skuRepository) {
        this.skuService = skuService;
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.iMoneyDiscount = iMoneyDiscount;
        this.orderRepository = orderRepository;
        this.skuRepository = skuRepository;
    }


    /**
     *  获取未支付订单信息
     * @param page 页码
     * @param size 条数
     */
    @Override
    public Page<Order> getUnpaid(Integer page, Integer size){
//      构建jpa 分页对象
        Pageable pageable = PageRequest.of(page, size,Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        Date date = new Date();
        Page<Order> orders = this.orderRepository.findByExpiredTimeGreaterThanAndStatusAndUserId(date, OrderStatus.UNPAID.value(), uid,pageable);
        return orders;
    }

    @Override
    public Page<Order> getOrderByStatus(Integer status, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size,Sort.by("createTime").descending());
        Long uid = LocalUser.getUser().getId();
        if(status == OrderStatus.All.value()){
            return this.orderRepository.findByUserId(uid,pageable);
        }
        return this.orderRepository.findByUserIdAndStatus(uid,status,pageable);
    }

    @Override
    public Optional<Order> getOrderDetail(Long oid) {
        Long uid = LocalUser.getUser().getId();
        return this.orderRepository.findFirstByUserIdAndId(uid, oid);
    }


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
    public OrderChecker isOk(Long uid, OrderDTO dto) {
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
            userCouponRepository.findFirstByUserIdAndCouponIdAndStatusAndOrderIdNull(uid, couponId,1).orElseThrow(() -> new NotFoundException(50006));
             couponChecker = new CouponChecker(coupon,iMoneyDiscount);
        }
        OrderChecker orderChecker = new OrderChecker(
                dto,skuList,couponChecker,this.maxSkuLimit
        );
        orderChecker.isOk();
        return orderChecker;
    }

    /**
     * 下单操作
     * @param uid 用户id
     * @param orderDTO 订单
     * @param orderChecker 订单校验结果
     * @return 订单id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long placeOrder(Long uid, OrderDTO orderDTO, OrderChecker orderChecker) {
        String orderNo = OrderUtil.makeOrderNo();
        Calendar now = Calendar.getInstance();
//        克隆当前时间 保证添加到数据库的时间和计算过期时间的开始时间一致
        Calendar placedTime = (Calendar) now.clone();
        Date expiredTime = CommonUtil.addSomeSeconds(now, this.payTimeLimit).getTime();
        Order order = Order.builder().orderNo(orderNo).totalPrice(orderDTO.getTotalPrice()).finalTotalPrice(orderDTO.getFinalTotalPrice())
                .userId(uid).totalCount(orderChecker.getTotalCount()).snapImg(orderChecker.getLeaderImg()).snapTitle(orderChecker.getLeaderTitle())
                .status(OrderStatus.UNPAID.value())
//        获取订单过期时间 当前时间加上规定时间 获取过期时间
                .expiredTime(expiredTime)
                .placedTime(placedTime.getTime())
                .build();

        order.setSnapAddress(orderDTO.getAddress());
        order.setSnapItems(orderChecker.getOrderSkuList());
        orderRepository.save(order);

//      预扣库存
        this.reduceStock(orderChecker);

//        核销优惠券 判断用户是否使用优惠券
        if (orderDTO.getCouponId()!=null){
            this.writeOffCoupon(orderDTO.getCouponId(),order.getId(),uid);
        }
        return order.getId();
    }

    /**
     * 核销优惠券
     * @param couponId 优惠券id
     * @param orderId 订单id
     * @param uid 用户id
     */
    private void writeOffCoupon(Long couponId,Long orderId,Long uid){
        Date now = new Date();
        int result = this.userCouponRepository.writeOff(couponId, orderId, uid, now);
//        如果result不为1 则表示核销优惠券的操作没执行
        if (result!=1){
            throw new ForbiddenException(40012);
        }
    }

    /**
     * 减库存
     * 扣减库存 可能存在的情况
     * 1 商品库存大于购买的数量 正常扣减
     * 2 商品库存小于购买数量 库存出现负数
     * 3 库存不足 报错
     * 4 加锁 数据库行锁/java代码加锁
     *
     *
     *  java 锁 在多线程 分布式 中无效 不能保证线程安全
     *  数据库 悲观锁（行锁） 高并发性能损耗大
     *      开启事务
     *      select ... from sku for update
     *      update set stock -count
     *      commit
     *      悲观锁 可以保证线程安全 资源访问有序  高并发情况下频发加锁释放锁对数据库性能损耗大
     *        乐观锁
     * @param orderChecker 订单校验结果类
     */
    private void reduceStock(OrderChecker orderChecker){
        List<OrderSku> orderSkuList = orderChecker.getOrderSkuList();
        for (OrderSku sku : orderSkuList) {
//            如果result不为1 则表示减库存的操作没执行
           int result = this.skuRepository.reduceStock(sku.getId(),sku.getCount().longValue());
            if (result!=1){
                throw  new ParameterException(50011);
            }
        }
    }

}
