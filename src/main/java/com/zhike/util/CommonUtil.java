package com.zhike.util;

import com.zhike.bo.PageCounter;

import java.math.BigDecimal;
import java.util.Calendar;
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

    public static Calendar addSomeSeconds(Calendar calendar, int seconds) {
        calendar.add(Calendar.SECOND, seconds);
        return calendar;
    }

    //period 单位：秒
    public static Boolean isOutOfDate(Date startTime, Long period) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long startTimeStamp = startTime.getTime();
        Long periodMillSecond = period * 1000;
        if (now > (startTimeStamp + periodMillSecond)) {
            return true;
        }
        return false;
    }

    public static Boolean isOutOfDate(Date expiredTime) {
        Long now = Calendar.getInstance().getTimeInMillis();
        Long expiredTimeStamp = expiredTime.getTime();
        if(now > expiredTimeStamp){
            return true;
        }
        return false;
    }

    /**
     * 获取10位的时间戳
     * @return
     */
    public static String timestamp10(){
        Long timestamp13 = Calendar.getInstance().getTimeInMillis();
        String timestamp13Str = timestamp13.toString();
        return timestamp13Str.substring(0, timestamp13Str.length() - 3);
    }

    /**
     * 将元转换为分
     * @param p
     * @return
     */
    public static String yuanToFenPlainString(BigDecimal p){
        p = p.multiply(new BigDecimal("100"));
        return CommonUtil.toPlain(p);
    }

    /**
     * 将分转换为字符串
     * @param p
     * @return
     */
    public static String toPlain(BigDecimal p){
        return p.stripTrailingZeros().toPlainString();
    }

}
