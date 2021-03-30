package com.zhike.repository;

import com.zhike.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * @author Administrator
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    /**
     * 获取未支付的订单
     * @param now 时间
     * @param status 状态
     * @param uid 用户id
     * @param pageable 分页对象
     * @return 订单集合
     *   查找过期时间小于传入的当前时间并符合状态和用户的条件
     */
    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date now, Integer status, Long uid,Pageable pageable);

    /**
     * 获取用户全部订单
     * @param uid 用户id
     * @param pageable 分页对象
     * @return 订单结果集
     */
    Page<Order> findByUserId(Long uid,Pageable pageable);

    /**
     * 根据状态获取订单 （不能获取未支付和已取消状态的订单）
     * @param uid 用户id
     * @param status 状态
     * @param pageable 分页对象
     * @return 订单结果集
     */
    Page<Order> findByUserIdAndStatus(Long uid,Integer status,Pageable pageable);

    /**
     * 获取订单详情
     * @param uid 用户id
     * @param oid 订单id
     * @return 订单详情
     */
    Optional<Order> findFirstByUserIdAndId(Long uid, Long oid);
}
