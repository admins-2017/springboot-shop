package com.zhike.dto;

import com.zhike.validators.password.PasswordEqual;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;

/**
 * @author Administrator
 * 数据传输对象
 *
 * 开启了Builder注解 默认的无参构造器则会改为私有方法 如果需要无参构造器或者全参构造器 则需要重写 将构造器改为公有
 * Builder构建对象 PersonDTO yuan = PersonDTO.builder().name("yuan").age(18).build();
 * 使用Builder 不通过序列化问题 Builder不会为属性生成get方法 如果需要序列化则需要手动添加get方法
 * Validated 开启Validated对该类的属性进行验证 如果上级调用放开启了Validated 则可以不加Validated
 * //@Validated
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordEqual(min = 2)
public class PersonDTO {

    @Length(min = 1,max = 8)
    private String name;
    @Range(min=18,max = 60)
    private Integer age;

    /**
     * 如果属性是对象或者其他需要做级联验证的 则需要在属性上开启Valid 进行级联校验
     * Valid 常用于开启级联
     */
    @Valid
    private SchoolDTO schoolDTO;

    private String password;

    private String newPassword;
}
