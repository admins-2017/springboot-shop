package com.zhike.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author Administrator
 */
@Data
public class SchoolDTO {

    @Length(max = 10)
    private String schoolName;
}
