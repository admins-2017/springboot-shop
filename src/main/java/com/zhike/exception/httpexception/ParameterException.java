package com.zhike.exception.httpexception;

/**
 * @author Administrator
 */
public class ParameterException extends HttpException{
    public ParameterException(Integer code){
        this.code =code;
        this.httpStatusCode =400;
    }
}
