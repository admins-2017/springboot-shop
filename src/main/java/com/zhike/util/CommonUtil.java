package com.zhike.util;

import com.zhike.bo.PageCounter;

import java.util.Date;

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

    public static Boolean isInTimeLine(Date date, Date start, Date end) {
        Long time = date.getTime();
        Long startTime = start.getTime();
        Long endTime = end.getTime();
        if (time > startTime && time < endTime) {
            return true;
        }
        return false;
    }
}
