package com.zhike.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author Administrator
 * 分页返回前端vo对象
 */
@Data
@NoArgsConstructor
public class PagingVO<T> {

    /**
     * 总记录数
     */
    private Long total;

    private Integer count;

    private Integer page;

    /**
     * 总页数
     */
    private Integer totalPage;

    private List<T> items;

    /**
     * 将数据库分页对象重构
     * @param page jpa分页返回的对象
     */
    public PagingVO(Page<T> page){
        this.init(page);
        this.items = page.getContent();
    }

    void init(Page<T> page){
        this.total = page.getTotalElements();
        this.page = page.getNumber();
        this.count = page.getSize();
        this.totalPage = page.getTotalPages();
    }
}
