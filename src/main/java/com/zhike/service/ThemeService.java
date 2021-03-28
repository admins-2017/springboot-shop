package com.zhike.service;

import com.zhike.model.Theme;

import java.util.List;
import java.util.Optional;

public interface ThemeService {

    /**
     * 根据一组名称获取主题
     * @param names 主题名称集合
     * @return 多个主题
     */
    List<Theme> findByNames(List<String> names);

    /**
     * 根据名称获取主题
     * @param name 主题名
     * @return 主题
     */
    Optional<Theme> findByName(String name);
}
