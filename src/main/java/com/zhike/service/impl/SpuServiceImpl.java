package com.zhike.service.impl;

import com.zhike.model.Spu;
import com.zhike.repository.SpuRepository;
import com.zhike.service.SpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 */
@Service
public class SpuServiceImpl implements SpuService {


    @Autowired
    private SpuRepository spuRepository;

    @Override
    public Spu getSpuDetailById(Long id) {
        return spuRepository.findOneById(id);
    }

    @Override
    public Page<Spu> getLatestSpu(Integer pageNum , Integer size) {
//      构建分页对象  Sort.by("createTime").descending() 根据createTime进行排序 descending倒序
        Pageable page = PageRequest.of(pageNum, size, Sort.by("createTime").descending());
        return spuRepository.findAll(page);
    }

    @Override
    public Page<Spu> getSpuListByCategoryId(Integer page, Integer count,Long id,Boolean isRoot) {
        Pageable pageable = PageRequest.of(page, count);
        if(isRoot){
            System.out.println("执行true");
         return spuRepository.findByRootCategoryIdOrderByCreateTimeDesc(id,pageable);
        }
        System.out.println("执行false");
        return spuRepository.findByCategoryIdOrderByCreateTimeDesc(id,pageable);
    }
}
