package com.zhike.service;

import com.zhike.model.Tag;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Administrator
 */
public interface TagService {
    /**
     * 根据标签类型获取所有
     * @param typeId 标签类型
     * @return 标签结果集
     */
    List<Tag> getAll(Integer typeId);

}
