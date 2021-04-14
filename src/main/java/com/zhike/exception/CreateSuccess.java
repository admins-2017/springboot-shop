package com.zhike.exception;


import com.zhike.exception.httpexception.HttpException;

/**
 * @author Administrator
 */
public class CreateSuccess extends HttpException {
    public CreateSuccess(int code){
        this.httpStatusCode = 201;
        this.code = code;
    }
}
