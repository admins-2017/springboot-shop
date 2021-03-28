package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "spu_detail_img", schema = "shop", catalog = "")
@Data
public class SpuDetailImg extends BaseEntity{
    @Id
    private Long id;
    private String img;
    private Long spuId;
    private Long index;

}
