package com.zhike.api.v1;

import com.zhike.exception.httpexception.NotFoundException;
import com.zhike.model.Category;
import com.zhike.model.GridCategory;
import com.zhike.service.CategoryService;
import com.zhike.service.GridCategoryService;
import com.zhike.vo.CategoryAllVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    private final GridCategoryService gridCategoryService;

    public CategoryController(CategoryService categoryService, GridCategoryService gridCategoryService) {
        this.categoryService = categoryService;
        this.gridCategoryService = gridCategoryService;
    }

    @RequestMapping("all")
    public CategoryAllVO getAll(){
        Map<Integer, List<Category>> map = categoryService.getAll();
        return new CategoryAllVO(map);
    }

    @RequestMapping("/grid/all")
    public List<GridCategory> getGridAll(){
        List<GridCategory> list = gridCategoryService.getGridCategoryList();
        if (list.isEmpty()){
            throw  new NotFoundException(30009);
        }
        return list;
    }

}
