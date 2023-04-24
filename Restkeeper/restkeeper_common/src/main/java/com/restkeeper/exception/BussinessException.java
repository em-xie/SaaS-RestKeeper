package com.restkeeper.exception;

/**
 * @作者：xie
 * @时间：2023/4/24 20:20
 */
public class BussinessException extends  RuntimeException{
    public BussinessException(String message) {
        super(message);
    }
}
