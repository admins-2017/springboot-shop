package com.zhike.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Data
public class Order extends BaseEntity{
    @Id
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Integer totalCount;
    private Date expiredTime;
    private Date placedTime;
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Boolean status;
}
