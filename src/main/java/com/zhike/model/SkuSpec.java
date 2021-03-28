package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "sku_spec", schema = "shop", catalog = "")
@Data
public class SkuSpec {
    @Id
    private Long id;
    private Long spuId;
    private Long skuId;
    private Long keyId;
    private Long valueId;


}
