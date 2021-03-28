package com.zhike.exception.HttpException;

/**
 * 未登录
 */
public class UnAuthenticatedException extends HttpException{

    public UnAuthenticatedException(int code){
        this.code = code;
        this.httpStatusCode = 401;
    }
}
