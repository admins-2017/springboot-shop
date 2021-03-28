package com.zhike.util;

import com.zhike.bo.PageCounter;

/**
 * @author Administrator
 */
public class CommonUtil {

    /**
     * 将页码和条数装换为开始记录数
     * @param page
     * @param size
     */
    public static PageCounter convertToPageParameter(Integer page,Integer size){
        int pageNum = page / size;
        PageCounter pageCounter = PageCounter.builder().page(pageNum).count(size).build();
        return pageCounter;
    }
}
