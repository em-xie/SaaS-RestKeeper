package com.restkeeper.response.exception;

import lombok.Data;

/**
 * @作者：xie
 * @时间：2023/4/22 15:49
 */
@Data
public class ExceptionResponse{
    private String msg;
    public ExceptionResponse(String msg){
        this.msg = msg;
    }
}
