package com.zhike.repository;

import com.zhike.model.Spu;
import com.zhike.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface TagRepository extends JpaRepository<Tag,Long> {

    /**
     * 根据类型获取标签
     * @param type 类型
     * @return 结果集
     */
    List<Tag> findAllByType(Integer type);

}
