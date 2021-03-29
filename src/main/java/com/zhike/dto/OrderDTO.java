package com.zhike.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
@Data
public class OrderDTO {

    /**
     * 原价
     */
    @DecimalMin(value="0.00", message = "不在合法范围内" )
    @DecimalMax(value="99999999.99", message = "不在合法范围内")
    private BigDecimal totalPrice;

    /**
     * 最终价格
     */
    private BigDecimal finalTotalPrice;

    /**
     * 如果使用优惠券 则传回id
     */
    private Long couponId;

    /**
     * 商品信息
     */
    private List<SkuInfoDTO> skuInfoList;

    /**
     * 地址
     */
    private OrderAddressDTO address;
}
