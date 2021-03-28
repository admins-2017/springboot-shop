package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "user_coupon", schema = "shop", catalog = "")
@Data
public class UserCoupon {
    @Id
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Timestamp createTime;
    private Long orderId;
    private Date updateTime;
}
