package com.zhike.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Data
public class Brand extends BaseEntity {
    @Id
    private Long  id;
    private String name;
    private String description;

}