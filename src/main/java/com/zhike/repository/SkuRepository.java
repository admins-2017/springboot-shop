package com.zhike.repository;

import com.zhike.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface SkuRepository extends JpaRepository<Sku,Long> {

    /**
     * 根据一组id获取sku
     * @param ids sku id
     * @return
     */
    List<Sku> findAllByIdIn(List<Long> ids);
}
