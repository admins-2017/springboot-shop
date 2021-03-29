package com.zhike.bo;

import com.zhike.dto.SkuInfoDTO;
import com.zhike.model.Sku;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author kang
 * 业务层传输对象 BO
 */
@Data
@NoArgsConstructor
public class SkuOrderBO {

    private BigDecimal actualPrice;
    private Long categoryId;
    private Integer count;

    public SkuOrderBO(Sku sku, SkuInfoDTO skuInfoDTO){
        this.actualPrice = sku.getActualPrice();
        this.count = skuInfoDTO.getCount();
        this.categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice(){
        return this.actualPrice.multiply(new BigDecimal(this.count));
    }
}
