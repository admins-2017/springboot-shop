package com.zhike.exception.HttpException;

public class ParmeterException extends HttpException{
    public ParmeterException(Integer code){
        this.code =code;
        this.httpStatusCode =4000;
    }
}
