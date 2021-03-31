package com.zhike.repository;

import com.zhike.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    /**
     * 减去库存 乐观锁写法
     * @param sid 商品id
     * @param quantity 减去数量
     * @return 更新行数
     * 一条sql语句的原子性 不会出现线程问题
     *  乐观锁 可以写成 select version from sku  update 。。。where version =... 俩条 sql
     *  也可以写成 一条sql
     *  and s.stock >= :quantity 乐观锁思想 保证 库存不会被减为负数
     *
     *  Modifying 在query做更新删除等操作 需要添加注解Modifying
     */
    @Modifying
    @Query("Update Sku s set s.stock = s.stock - :quantity\n" +
            "where s.id = :sid " +
            "and s.stock >= :quantity")
    int reduceStock(Long sid,Long quantity);

    /**
     * 执行归还库存 将以取消的订单sku的库存修改
     * @param sid 商品id
     * @param quantity 归还数量
     * @return 更新记录数
     */
    @Modifying
    @Query("update Sku s set s.stock=s.stock+(:quantity) where s.id = :sid")
    int recoverStock(@Param("sid") Long sid,
                     @Param("quantity") Long quantity);
}
