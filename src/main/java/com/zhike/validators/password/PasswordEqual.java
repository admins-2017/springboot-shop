package com.zhike.validators.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Administrator
 * 自定义Validated校验注解
 *
 * Documented 元注解
 * Retention(RetentionPolicy.RUNTIME) 声明注解为运行时注解
 * Target(ElementType.TYPE) 指定该注解用于类上
 * ElementType.METHOD 方法上
 *
 * Constraint(validatedBy = PasswordValidator.class) 用于绑定该注解的校验逻辑类 验证是否通过校验
 * 可以指定多个处理类 验证多个业务逻辑
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Constraint(validatedBy = PasswordValidator.class)
public @interface PasswordEqual {

    /**
     * 如果验证不通过接收的信息 默认
     */
    String message() default "俩次输入的密码不一致";

    /**
     * 设置密码最短长度
     * @return
     */
    int min() default 6;

    /**
     * 设置密码最长长度
     * @return
     */
    int max() default 12;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

