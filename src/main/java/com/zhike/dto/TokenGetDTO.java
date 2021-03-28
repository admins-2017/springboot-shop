package com.zhike.dto;

import com.zhike.core.enumeration.LoginType;
import com.zhike.validators.token.TokenPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TokenGetDTO {

    @NotBlank(message = "account不可以为空")
    private String account;

    @TokenPassword(min = 5,max = 30,message = "{token.password}")
    private String password;
    /**
     * 登录类型
     */
    private LoginType type;
}
