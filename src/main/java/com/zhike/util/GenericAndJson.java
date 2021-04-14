package com.zhike.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhike.exception.httpexception.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * 将json（单体，数组）转换为任意对象
 */
@Component
public class GenericAndJson {

    /**
     * 静态方法中使用
     */
    private static ObjectMapper mapper;

    /**
     * 对 静态变量 注入 否则静态方法中无法使用
     * @param mapper
     */
    @Autowired
    public void setMapper(ObjectMapper mapper) {
        GenericAndJson.mapper = mapper;
    }

    /**
     * 将传入的object 序列化
     * @param o 传入对象
     * @param <T>
     * @return 返回字符串
     */
    public static <T> String objectToJson(T o){
        try {
            return GenericAndJson.mapper.writeValueAsString(o);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

    /**
     * 将字符串反序列化 成泛型
     * @param s 字符串
     * @param tr 泛型指定的对象
     * @param <T>
     * @return 泛型指定的对象
     */
    public static <T> T jsonToObject(String s,  TypeReference<T> tr) {
        if (s == null) {
            return null;
        }
        try {
            T o = GenericAndJson.mapper.readValue(s, tr);
            return o;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }

//    public static <T> List<T> jsonToList(String s ) {
//        if (s == null) {
//            return null;
//        }
//        try {
////            T.class
//            List<T> list = GenericAndJson.mapper.readValue(s, new TypeReference<List<T>>() {
//            });
//            return list;
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//            throw new ServerErrorException(9999);
//        }
//    }
}
