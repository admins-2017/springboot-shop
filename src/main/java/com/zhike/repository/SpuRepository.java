package com.zhike.repository;

import com.zhike.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface SpuRepository extends JpaRepository<Spu,Long> {

    /**
     * 根据id查询
     * @param id 商品id
     * @return 商品详情
     */
    Spu findOneById(Long id);

    /**
     * 根据分类id获取商品信息 按创建时间倒序排列
     * @param id
     * @param pageable
     * @return
     */
    Page<Spu> findByCategoryIdOrderByCreateTimeDesc(Long id, Pageable pageable);

    /**
     * 根据一级分类id获取商品信息 按创建时间倒序排列
     * @param id
     * @param pageable
     * @return
     */
    Page<Spu> findByRootCategoryIdOrderByCreateTimeDesc(Long id, Pageable pageable);


    /**
     * 根据查询条件获取商品
     * @param likeName 查询条件
     * @param pageable 分页对象
     * @return 分页结果集
     */
    Page<Spu> findAllByTagsLike(Pageable pageable, String likeName);
}
