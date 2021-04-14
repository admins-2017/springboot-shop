package com.zhike.validators.token;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 用户登录验证密码注解
 * @author Administrator
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.FIELD})
@Constraint(validatedBy = TokenPasswordValidator.class)
public @interface TokenPassword {

    /**
     * 如果验证不通过接收的信息 默认
     */
    String message() default "用户名或密码错误，请重试";

    /**
     * 设置密码最短长度
     * @return
     */
    int min() default 6;

    /**
     * 设置密码最长长度
     * @return
     */
    int max() default 32;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
