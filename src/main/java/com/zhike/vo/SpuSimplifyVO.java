package com.zhike.vo;

import lombok.Data;

/**
 * @author Administrator
 */
@Data
public class SpuSimplifyVO {
    private Long id;
    private String title;
    private String subtitle;
    private String price;
    private Integer sketchSpecId;
    private String img;
    private String discountPrice;
    private String description;
    private String tags;
    private String forThemeImg;
}
