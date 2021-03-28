package com.zhike.repository;

import com.zhike.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface GridCategoryRepository extends JpaRepository<GridCategory,Long> {
}
