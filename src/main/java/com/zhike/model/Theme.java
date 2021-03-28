package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Data
public class Theme extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String description;
    private String name;
    /**
     * 模板名称
     */
    private String tplName;
    private String entranceImg;
    private String extend;
    private String internalTopImg;
    private String titleImg;
    private Boolean online;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="theme_spu", joinColumns = @JoinColumn(name="theme_id")
            , inverseJoinColumns = @JoinColumn(name="spu_id"))
    private List<Spu> spuList;
}
