package com.zhike.service.impl;

import com.zhike.model.Category;
import com.zhike.repository.CategoryRepository;
import com.zhike.service.CategoryService;
import com.zhike.vo.CategoryAllVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Map<Integer,List<Category>> getAll() {
        List<Category> roots = categoryRepository.findAllByIsRootOrderByIndexAsc(true);
        List<Category> subs = categoryRepository.findAllByIsRootOrderByIndexAsc(false);
        Map<Integer,List<Category>> categories = new HashMap<>(10);
        categories.put(1,roots);
        categories.put(2,subs);
        return categories;
    }
}
