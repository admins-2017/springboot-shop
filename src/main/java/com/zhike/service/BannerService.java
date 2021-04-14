package com.zhike.service;

import com.zhike.model.Banner;

/**
 * @author Administrator
 */
public interface BannerService {

    /**
     * 根据名称获取banner
     * @param name banner名称
     * @return banner对象
     */
    Banner getByName(String name);

    /**
     * 根据id获取banner
     * @param id
     * @return
     */
    Banner getById(Long id);
}
