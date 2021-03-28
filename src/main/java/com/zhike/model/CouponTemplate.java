package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "coupon_template", schema = "shop", catalog = "")
@Data
public class CouponTemplate extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String description;
    private BigDecimal fullMoney;
    private BigDecimal minus;
    private BigDecimal discount;
    private short type;

}
