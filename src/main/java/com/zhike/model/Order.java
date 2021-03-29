package com.zhike.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author Administrator
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    /**
     * 订单编号
     */
    private String orderNo;
    private Long userId;
    /**
     * 订单原始总价格
     */
    private BigDecimal totalPrice;
    /**
     * 当前订单商品数量
     */
    private Integer totalCount;
    /**
     *
     */
    private Date expiredTime;
    /**
     *
     */
    private Date placedTime;
    /**
     *  快照图片
     */
    private String snapImg;
    /**
     *  快照标题
     */
    private String snapTitle;
    /**
     *  购买商品信息
     */
    private String snapItems;
    /**
     * 地址
     */
    private String snapAddress;
    /**
     *  支付id
     */
    private String prepayId;
    /**
     * 订单最终价格
     */
    private BigDecimal finalTotalPrice;
    /**
     *  订单状态
     */
    private Boolean status;
}
