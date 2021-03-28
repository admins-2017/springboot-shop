package com.zhike.vo;

import com.zhike.model.Category;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author Administrator
 */
@Data
public class CategoryPureVo {
    private Long id;
    private String name;
    private String description;
    private Boolean isRoot;
    private Long parentId;
    private String img;
    private Long index;

    public CategoryPureVo(Category category) {
        BeanUtils.copyProperties(category,this);
    }
}
