package com.zhike.vo;


import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 继承分页泛型对象
 * @author Administrator
 */
public class PagingDozer<T,K> extends PagingVO{

    /**
     * 将数据库返回的分页对象转换为前对分页vo对象
     * @param page 数据库分页对象
     * @param kClass 前端vo对象
     */
    public PagingDozer(Page<T> page,Class<K> kClass){
        this.init(page);
        List<T> items = page.getContent();
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        List<K> vos = new ArrayList<>();
        items.forEach(item->{
            K vo = mapper.map(item,kClass);
            vos.add(vo);
        });
        this.setItems(vos);
    }

}
