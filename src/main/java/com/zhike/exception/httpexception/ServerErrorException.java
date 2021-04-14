package com.zhike.exception.httpexception;

/**
 * @author Administrator
 * 服务器未知异常
 */
public class ServerErrorException extends HttpException{

    public ServerErrorException(Integer code){
        this.code =code;
        this.httpStatusCode =500;
    }
}
