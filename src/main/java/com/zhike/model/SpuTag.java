package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "spu_tag", schema = "shop", catalog = "")
@Data
public class SpuTag {
    @Id
    private Long id;
    private Long spuId;
    private Long tagId;

}
