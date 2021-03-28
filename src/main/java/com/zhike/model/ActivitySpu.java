package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "activity_spu", schema = "shop", catalog = "")
@Data
public class ActivitySpu {
    @Id
    private Long id;
    private Long activityId;
    private Long spuId;
    private Boolean participation;

}
