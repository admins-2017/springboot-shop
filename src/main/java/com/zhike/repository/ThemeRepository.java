package com.zhike.repository;

import com.zhike.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author Administrator
 */
@Repository
public interface ThemeRepository extends JpaRepository<Theme,Long> {

    /**
     * 根据一组名称获取多个主题
     * @param names
     * @return
     * select t from Theme t where t.name in (:names) 面向对象写的sql t为Theme对象
     */
    @Query("select t from Theme t where t.name in (:names)")
    List<Theme> findByNames(List<String> names);

    /**
     * 根据名称查找主题
     * @param name
     * @return
     */
    Optional<Theme> findByName(String name);
}
