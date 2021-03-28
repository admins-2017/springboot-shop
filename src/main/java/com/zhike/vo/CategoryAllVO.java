package com.zhike.vo;

import com.zhike.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
public class CategoryAllVO {

    private List<CategoryPureVo> roots;

    private List<CategoryPureVo> subs;

    public CategoryAllVO(Map<Integer,List<Category>> map) {
        List<Category> roots = map.get(1);
        List<Category> subs = map.get(2);
//        将原始集合 List<Category> 转换为新的集合 List<CategoryPureVo>
        this.roots = roots.stream().map(CategoryPureVo::new).collect(Collectors.toList());
        this.subs = subs.stream().map(CategoryPureVo::new).collect(Collectors.toList());
    }
}
