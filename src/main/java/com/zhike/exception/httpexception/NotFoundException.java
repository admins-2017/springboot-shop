package com.zhike.exception.httpexception;

/**
 * 自定义404异常类
 * @author Administrator
 */
public class NotFoundException extends HttpException{

    public NotFoundException(Integer code){
        this.code =code;
        this.httpStatusCode =404;
    }
}
