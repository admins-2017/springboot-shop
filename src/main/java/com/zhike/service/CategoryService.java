package com.zhike.service;

import com.zhike.model.Category;
import com.zhike.vo.CategoryAllVO;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public interface CategoryService {

    /**
     * 获取所有分类
     * @return
     */
    public Map<Integer, List<Category>> getAll();
}
