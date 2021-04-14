package com.zhike.validators.token;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * tokenPassword 注解 处理类
 * @author Administrator
 */
public class TokenPasswordValidator implements ConstraintValidator<TokenPassword, String> {

    private int min;
    private int max;

    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.max = constraintAnnotation.max();
        this.min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
//        判断密码是否为空
        if(StringUtils.isEmpty(s)){
            return true;
        }
        return false;
    }
}
