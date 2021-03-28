package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "coupon_type", schema = "shop", catalog = "")
@Data
public class CouponType extends BaseEntity{
    @Id
    private Long  id;
    private String name;
    private int code;
    private String description;

}
