package com.zhike.service.impl;

import com.zhike.model.GridCategory;
import com.zhike.repository.GridCategoryRepository;
import com.zhike.service.GridCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class GridCategoryServiceImpl implements GridCategoryService {

    private final GridCategoryRepository gridCategoryRepository;

    public GridCategoryServiceImpl(GridCategoryRepository gridCategoryRepository) {
        this.gridCategoryRepository = gridCategoryRepository;
    }

    @Override
    public List<GridCategory> getGridCategoryList(){
        return gridCategoryRepository.findAll();
    }
}
