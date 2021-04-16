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
public class Tag extends BaseEntity{
    @Id
    private Long id;
    private String title;
    private String description;
    private Integer highlight;
    private Integer type;

}
