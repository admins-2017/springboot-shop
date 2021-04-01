package com.zhike.repository;

import com.zhike.model.SaleExplain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Administrator
 */
@Repository
public interface SaleExplainRepository extends JpaRepository<SaleExplain,Long> {

    /**
     * 获取存在的信息
     * @param fixed 判断是否有值
     * @return 集合
     */
    List<SaleExplain> findByFixedOrderByIndex(boolean fixed);
}
