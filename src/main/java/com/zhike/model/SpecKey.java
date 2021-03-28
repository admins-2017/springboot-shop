package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "spec_key", schema = "shop", catalog = "")
@Data
public class SpecKey extends BaseEntity{
    @Id
    private Long id;
    //规格名的名称 例如 颜色，图案...
    private String name;
    //规格单位 件，盒，箱
    private String unit;
    //是否为标准规格
    private Boolean standard;
    private String description;

}
