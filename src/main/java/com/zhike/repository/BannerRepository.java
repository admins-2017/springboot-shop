package com.zhike.repository;

import com.zhike.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 自定义的repository 继承JpaRepository<Banner,Long>
 * JpaRepository<Banner,Long>  Banner 实体类 long id的类型
 * @author Administrator
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner,Long> {
    /**
     * 根据banner id 进行查询
     * @param id id
     * @return banner对象
     */
    Banner findOneById(Long id);

    /**
     * 根据名称获取banner信息
     * @param name 名称
     * @return banner
     */
    Banner findOneByName(String name);
}
