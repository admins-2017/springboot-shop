package com.zhike.service;

import com.zhike.model.Spu;
import org.springframework.data.domain.Page;


/**
 * @author Administrator
 */
public interface SpuService {

    /**
     * 根据商品id查询
     * @param id 商品id
     * @return 商品详情
     */
    Spu getSpuDetailById(Long id);

    /**
     * 获取最新数据
     * @param pageNum 页码
     * @param size 条数
     * @return 最新数据
     */
    Page<Spu> getLatestSpu(Integer pageNum, Integer size);

    /**
     * 根据分类id获取商品信息
     * @param page 页码
     * @param count 条数
     * @param id 分类id
     * @param isRoot 是否为一级分类
     * @return 商品集合
     */
    Page<Spu> getSpuListByCategoryId(Integer page, Integer count,Long id,Boolean isRoot);
}
