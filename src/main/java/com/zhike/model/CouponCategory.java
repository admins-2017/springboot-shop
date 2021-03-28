package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "coupon_category", schema = "shop", catalog = "")
@Data
public class CouponCategory {
    @Id
    private Long id;
    private Long categoryId;
    private Long couponId;

}
