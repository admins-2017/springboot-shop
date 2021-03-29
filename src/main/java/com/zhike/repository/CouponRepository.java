package com.zhike.repository;

import com.zhike.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon,Long> {

    /**
     * 根据分类id获取当前在使用期间的优惠券
     * 不获取已过期的优惠券和活动结束的优惠券
     * @param cid 分类id
     * @param now 当前时间
     * @return
     *  面向对象的sql
     *  Coupon c 操作优惠券类
     *  categoryList 属性多对多 相当于操作Category和coupon 的中间表 将 符合cid 并和coupon_id 关联的 分类获取
     *  因为在实体类中配置了多对多的导航 Category 和 coupon
     *  在Category中已经声明了第三张中间表 @JoinTable(name = "coupon_category",
     *             joinColumns = @JoinColumn(name = "category_id"),
     *             inverseJoinColumns = @JoinColumn(name = "coupon_id"))
     *  而在 Coupon中 设置了 categoryList 的 mappedBy = "couponList"   形成对应关系
     *  这里就可以使用 categoryList 获取中间表中符合的数据
     *
     *  join Activity a on a.id = c.activityId 加入活动类 如果是join类 则需要on 来指定关联关系
     */
    @Query("select c from Coupon c \n" +
            "join c.categoryList ca \n" +
            "join Activity a on a.id = c.activityId \n" +
            "where ca.id = :cid and a.startTime < :now and a.endTime > :now\n")
    List<Coupon> findByCategory(Long cid, Date now);

    /**
     * 获取全场券
     * @param b 是否为全场券
     * @param now 当前时间
     * @return
     */
    @Query("select c from Coupon c\n" +
            "join Activity a on c.activityId = a.id\n" +
            "where c.wholeStore = :isWholeStore\n" +
            "and a.startTime < :now and a.endTime > :now")
    List<Coupon> findByWholeStore(boolean isWholeStore, Date now);

    /**
     * 获取所有可以使用的优惠券
     * @param uid
     * @param now
     * @return
     */
    @Query("select c from Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where uc.status = 1 \n" +
            "and u.id = :uid\n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now\n" +
            "and uc.orderId is null")
    List<Coupon> findMyAvailable(Long uid, Date now);


    /**
     * 获取所有已经使用的优惠券
     * @param uid
     * @param now
     * @return
     */
    @Query("select c From Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.status = 2\n" +
            "and uc.orderId is not null \n" +
            "and c.startTime < :now\n" +
            "and c.endTime > :now")
    List<Coupon> findMyUsed(@Param("uid") Long uid, @Param("now") Date now);


    /**
     * 获取所有过期的优惠券
     * @param uid
     * @param now
     * @return
     */
    @Query("select c From Coupon c\n" +
            "join UserCoupon uc\n" +
            "on c.id = uc.couponId\n" +
            "join User u\n" +
            "on u.id = uc.userId\n" +
            "where u.id = :uid\n" +
            "and uc.orderId is null\n" +
            "and uc.status <> 2\n" +
            "and c.endTime < :now")
    List<Coupon> findMyExpired(Long uid, Date now);
}
