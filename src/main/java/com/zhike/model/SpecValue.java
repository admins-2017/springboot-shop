package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "spec_value", schema = "shop", catalog = "")
@Data
public class SpecValue extends BaseEntity{
    @Id
    private Long id;
    private String value;
    private Long specId;
    private String extend;

}
