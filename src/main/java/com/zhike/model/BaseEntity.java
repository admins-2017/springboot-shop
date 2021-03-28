package com.zhike.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * 实体基类
 * @author Administrator
 * MappedSuperclass 表明该类为映射的基类 只要继承的方法 数据库查询的三个字段自动映射
 */
@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    /**
     * JsonIgnore 修饰的属性不序列化返回前端
     */
    @JsonIgnore
    private Date createTime;
    @JsonIgnore
    private Date updateTime;
    @JsonIgnore
    private Date deleteTime;
}
