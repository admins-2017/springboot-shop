package com.zhike.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author Administrator
 * Where(clause = "delete_time is null ") 在每次查询语句 查询表时添加一个查询条件
 */
@Entity
@Table(name = "banner", schema = "shop", catalog = "")
@Data
@Where(clause = "delete_time is null ")
public class Banner extends BaseEntity{
    @Id

    private Long id;
    private String name;
    private String description;
    private String title;
    private String img;

    /**
     *     设置一对多 外键bannerId
     */
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "bannerId")
    private List<BannerItem> items;
}
