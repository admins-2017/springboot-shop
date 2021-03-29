package com.zhike.repository;

import com.zhike.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Administrator
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity,Long> {

    /**
     * 根据名称获取活动信息
     * @param name 活动名称
     * @return 活动信息
     */
    Activity findByName(String name);

    /**
     * 查询优惠券对应的活动是否过期
     * 根据属性couponList 做join查询 俩个类必须要有导航关系才可以
     * 相当于 join coupon
     * 条件为 coupon 的id 是否等于 传入的 id
     * @param id
     * @return
     */
    Optional<Activity> findByCouponListId(Long id);
}
