package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "activity_category", schema = "shop")
@Data
public class ActivityCategory {
    @Id
    private Long id;
    private Long categoryId;
    private int activityId;

}
