package com.zhike.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * 活动实体类
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null and online = 1")
public class Activity extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String description;
    /**
     * 活动开始时间
     */
    private Date startTime;
    /**
     * 活动结束时间
     */
    private Date endTime;
    private String remark;
    /**
     * 是否上线
     */
    private boolean online;
    /**
     * 活动入口图片
     */
    private String entranceImg;
    /**
     * 活动详情顶部图片
     */
    private String internalTopImg;
    private String name;

    /**
     * 定义一对多关系
     * 在查询时通过activityId 到 Coupon类中查询所有activityId的优惠券
     * 在jpa中类可以看作数据表
     * JoinColumn("activityId") 相当于join coupon on activity.id = coupon.activity_id
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "activityId")
    private List<Coupon> couponList;
}
