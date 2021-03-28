package com.zhike.exception.HttpException;

/**
 * 自定义404异常类
 */
public class NotFoundException extends HttpException{

    public NotFoundException(Integer code){
        this.code =code;
        this.httpStatusCode =404;
    }
}
