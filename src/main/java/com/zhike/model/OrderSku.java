package com.zhike.model;

import com.zhike.dto.SkuInfoDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Administrator
 */
@Getter
@Setter
@NoArgsConstructor
public class OrderSku {
    private Long id;
    private Long spuId;
    /**
     *  总价 单价*数量
     */
    private BigDecimal finalPrice;
    /**
     * 一个商品的单价
     */
    private BigDecimal singlePrice;
    /**
     * 规格值
     */
    private List<String> specValues;
    /**
     * 购买数量
     */
    private Integer count;
    private String img;
    private String title;

    public OrderSku(Sku sku, SkuInfoDTO skuInfoDTO) {
        this.id = sku.getId();
        this.spuId = sku.getSpuId();
        this.singlePrice = sku.getActualPrice();
        this.finalPrice = sku.getActualPrice().multiply(new BigDecimal(skuInfoDTO.getCount()));
        this.count = skuInfoDTO.getCount();
        this.img = sku.getImg();
        this.title = sku.getTitle();
        this.specValues = sku.getSpecValueList();
    }
}
