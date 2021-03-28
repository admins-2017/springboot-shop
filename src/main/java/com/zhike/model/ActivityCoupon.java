package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "activity_coupon", schema = "shop", catalog = "")
@Data
public class ActivityCoupon {
    @Id
    private Long id;
    private Long couponId;
    private Long activityId;

}
