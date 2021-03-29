package com.zhike.repository;

import com.zhike.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Administrator
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
