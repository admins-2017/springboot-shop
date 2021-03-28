package com.zhike.repository;

import com.zhike.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    /**
     * 根据是否为根节点查询所有并排序
     * @param isRoot 是否为根节点
     * @return 分类集合
     */
    List<Category> findAllByIsRootOrderByIndexAsc(Boolean isRoot);
}
