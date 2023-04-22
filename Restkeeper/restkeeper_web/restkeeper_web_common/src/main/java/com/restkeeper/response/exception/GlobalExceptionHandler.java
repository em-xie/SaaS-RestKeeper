package com.restkeeper.response.exception;

/**
 * @作者：xie
 * @时间：2023/4/22 15:46
 */

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 拦截异常并转换成 自定义ExceptionResponse
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Object Exception(Exception ex) {
        ExceptionResponse response =new ExceptionResponse(ex.getMessage());
        return response;
    }
}
