package com.zhike.exception;


import com.zhike.exception.httpexception.HttpException;

/**
 * @author Administrator
 */
public class DeleteSuccess extends HttpException {
    public DeleteSuccess(int code){
        this.httpStatusCode = 200;
        this.code = code;
    }
}
