package com.zhike.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Data
@Where(clause = "delete_time is null")
public class Coupon {
    @Id
    private Long id;
    private String title;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    private String description;
    /**
     * 需要满减的满金额
     * 例：满100减10 需要满100
     */
    private BigDecimal fullMoney;
    /**
     * 需要满减的减金额
     * 例：满100减10 减10
     */
    private BigDecimal minus;
    /**
     * 用于折扣券
     * 打几折
     */
    private BigDecimal rate;
    /**
     * 优惠券类型
     */
    private Integer type;
    /**
     * 活动外键
     */
    private Long  activityId;
    /**
     * 用于描述优惠券的使用场景
     */
    private String remark;
    /**
     * 是否为全场券
     */
    private Boolean wholeStore;

    @ManyToMany(fetch = FetchType.LAZY,mappedBy = "couponList")
    private List<Category> categoryList;
}
