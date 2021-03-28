package com.zhike.core.enumeration;

public enum LoginType {
    USER_WX(0,"微信登录"),
    USER_EMAIL(1,"邮箱登录"),
    USER_PHONE(2,"手机号登录");

    private Integer value;

    LoginType(Integer value,String description){
        this.value = value;
    }
}
