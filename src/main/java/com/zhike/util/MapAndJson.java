package com.zhike.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhike.exception.httpexception.ServerErrorException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * 将map 转换为 json
 * 将 json 转换为 map 对象
 * jpa在做对象的序列化或反序列化时 执行方法 将String类型转换为map类型到前端 或将前端传入的map对象转换为String存入数据库
 *
 * 继承 AttributeConverter<T,K>
 *
 *  T 要装换为对象的类型
 *  K 在数据库中的类型 json类型在java中为String类型
 *
 *  Converter 该注解可以使jpa在做序列化或反序列化时执行该类的方法
 */
@Converter
public class MapAndJson implements AttributeConverter<Map<String,Object>,String> {

    /**
     * 将springboot自带的序列化json对象注入
     */
    @Autowired
    private ObjectMapper mapper;
    /**
     * 将模型对象转换为数据库字段
     * @param stringObjectMap 传入map对象
     * @return 存入数据库json类型的String
     */
    @Override
    public String convertToDatabaseColumn(Map<String, Object> stringObjectMap) {
//        将map对象序列化成String
        try {
            return mapper.writeValueAsString(stringObjectMap);
        } catch (Exception e) {
            e.printStackTrace();
//          捕获运行时异常并抛出
            throw new ServerErrorException(9999);
        }
    }

    /**
     * 将数据库的字段的值转换为模型对象
     * @param s 获取数据库中json类型的值
     * @return map对象
     */
    @Override
    public Map<String, Object> convertToEntityAttribute(String s) {
        /**
         * 将String 反序列化成 map 对象
         * 参数一: 反序列化的字符串
         * 参数二： 目标类型的class
         */
        try {
            if (s == null){
                return null;
            }
            return mapper.readValue(s, HashMap.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServerErrorException(9999);
        }
    }
}
