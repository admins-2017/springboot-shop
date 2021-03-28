package com.zhike.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "grid_category", schema = "shop", catalog = "")
@Data
@Where(clause = "delete_time is null")
public class GridCategory extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String img;
    private String name;
    private Long categoryId;
    private Long rootCategoryId;

}
