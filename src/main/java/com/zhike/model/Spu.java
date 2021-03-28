package com.zhike.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.List;

/**
 * @author Administrator
 */
@Entity
@Data
@Where(clause = "delete_time is null and online = 1 ")
public class Spu extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String subtitle;
    //二级分类id
    private Long categoryId;
    //用于一级分类查询的一级分类id
    private Long rootCategoryId;
    private Boolean online;
    private String price;
    private Integer sketchSpecId;
    private Integer defaultSkuId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private Boolean isTest;
    private String spuThemeImg;
    private String forThemeImg;

    /**
     * 建立一对多关系 并执行懒加载
     * JoinColumn(name = "spuId") name为sku实体类中的spu外键
     * 建立一对多后根据spuid获取到sku表中的数据
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<Sku> skuList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuImg> spuImgList;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "spuId")
    private List<SpuDetailImg> spuDetailImgList;
}
