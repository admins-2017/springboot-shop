package com.zhike.exception.httpexception;

/**
 * 自定义权限不足异常类
 * @author Administrator
 */
public class ForbiddenException extends HttpException{
    public ForbiddenException(Integer code){
        this.code = code;
        this.httpStatusCode= 403;
    }
}
