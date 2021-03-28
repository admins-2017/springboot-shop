package com.zhike.validators.password;


import com.zhike.dto.PersonDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Administrator
 * 与PasswordEqual关联类 用于处理PasswordEqual注解的验证
 *
 *  ConstraintValidator<PasswordEqual,PersonDTO>
 *  PasswordEqual 参数1 ：对应的是关联的注解类
 *  PersonDTO 参数2 自定义注解修饰的目标的类型 这里注解用于PersonDTO的密码验证 所以目标类型为PersonDTO
 */
public class PasswordValidator implements ConstraintValidator<PasswordEqual, PersonDTO> {

    private int min;

    private int max;

    /**
     * 获取注解中的参数 如果用户设置参数 则获取设置的参数 反之获取的就是注解设置的默认值
     * @param constraintAnnotation
     */
    @Override
    public void initialize(PasswordEqual constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    /**
     * 校验逻辑的方法 校验前后密码是否一致
     * @param personDTO 目标类
     * @param constraintValidatorContext
     * @return
     */
    @Override
    public boolean isValid(PersonDTO personDTO, ConstraintValidatorContext constraintValidatorContext) {
        String password1 = personDTO.getPassword();
        String password2 = personDTO.getNewPassword();
        boolean match = password1.equals(password2);
        if(password1.length() > max || password1.length() < min){
            return false;
        }
        if(password2.length() > max || password2.length() < min){
            return false;
        }
        return match;
    }
}
