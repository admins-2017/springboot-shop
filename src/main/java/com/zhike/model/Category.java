package com.zhike.model;

import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author Administrator
 */
@Entity
@Data
@Where(clause = "delete_time is null and online = 1")
public class Category extends BaseEntity{
    @Id
    private Long id;
    private String name;
    private String description;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;
    private Integer online;
    private Long level;


}
