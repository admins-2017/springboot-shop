package com.zhike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zhike.util.GenericAndJson;
import lombok.Data;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Administrator
 */
@Entity
@Data
@Where(clause = "delete_time is null and online = 1")
public class Sku extends BaseEntity{
    @Id
    private Long id;
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Boolean online;
    private String img;
    private String title;
    /**
     *     商品id
     */
    private Long spuId;

    /**
     *  sku唯一标识 code码
     */
    private String code;
    /**
     *     库存
     */
    private Long stock;
    private Long categoryId;
    private Long rootCategoryId;
    /**
     * 规格
     * 将json数组装换为list对象
     * @Convert(converter = ListAndJson.class) 声明jpa在序列化或反序列化时执行自定义封装的类进行序列化
     */
//    @Convert(converter = ListAndJson.class)
    private String specs;

    /**
     * 获取商品的所有规格值
     *
     * JsonIgnore 不进行序列化
     * @return
     */
    @JsonIgnore
    public List<String> getSpecValueList() {
        return this.getSpecs().stream().map(Spec::getValue).collect(Collectors.toList());
    }

    /**
     * 获取商品的价格 如果有折扣价 则返回折扣价 反之返回原价
     * @return
     */
    public BigDecimal getActualPrice(){
        return discountPrice==null?this.price:this.discountPrice;
    }

    /**
     * 在jpa执行序列化时会执行get 和 set 方法 所有需要重写get 和 set方法 来执行自定义序列化
     * @return
     */
    public List<Spec> getSpecs() {
//        判断属性是否有值 规格是否存在
        if (this.specs == null) {
            return Collections.emptyList();
        }
//     new TypeReference<List<Spec>>(){} 构建一个List<Spec>对象
        return GenericAndJson.jsonToObject(this.specs, new TypeReference<List<Spec>>(){});
    }


    /**
     * 在jpa执行序列化时会执行get 和 set 方法 所有需要重写get 和 set方法 来执行自定义序列化
     * @return
     */
    public void setSpecs(List<Spec> specs) {
        if(specs.isEmpty()){
            return;
        }
        this.specs = GenericAndJson.objectToJson(specs);
    }


    /**
     * 将mysql中单体json格式的类型装换为对象
     * 单体json 只有一个json体且不为数组json
     * 如果只有一个json体则可以用map来获取
     * Convert(converter = MapAndJson.class) 声明jpa在对该属性做序列化或反序列化时 执行声明类中的序列化方法
     * @Convert(converter = MapAndJson.class)
     * private Map<String,Object> test;
     */
}
