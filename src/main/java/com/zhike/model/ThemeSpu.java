package com.zhike.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Administrator
 */
@Entity
@Table(name = "theme_spu", schema = "shop", catalog = "")
@Data
public class ThemeSpu {
    @Id
    private Long id;
    private Long themeId;
    private Long spuId;

}
