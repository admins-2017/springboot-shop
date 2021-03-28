package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "sale_explain", schema = "shop", catalog = "")
@Data
public class SaleExplain extends BaseEntity {
    @Id
    private Long id;
    private Boolean fixed;
    private String text;
    private Integer spuId;
    private Integer index;
    private Integer replaceId;
}
