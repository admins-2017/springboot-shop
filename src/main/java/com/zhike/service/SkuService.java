package com.zhike.service;

import com.zhike.model.Sku;

import java.util.List;

/**
 * @author Administrator
 */
public interface SkuService {

    /**
     * 根据id获取一组sku
     * @param ids skuid
     * @return 结果
     */
    List<Sku> getSkuListByIds(List<Long> ids);
}
