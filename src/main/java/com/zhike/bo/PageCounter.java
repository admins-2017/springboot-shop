package com.zhike.bo;

import lombok.Builder;
import lombok.Data;

/**
 * @author Administrator
 */
@Data
@Builder
public class PageCounter {

    private Integer page;

    private Integer count;
}
