package com.zhike.exception.HttpException;

/**
 * 自定义权限不足异常类
 */
public class ForbiddenException extends HttpException{
    public ForbiddenException(Integer code){
        this.code = code;
        this.httpStatusCode= 403;
    }
}
