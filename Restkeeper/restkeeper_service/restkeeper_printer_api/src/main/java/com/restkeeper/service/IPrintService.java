package com.restkeeper.service;

import com.restkeeper.print.PrintContent;

/**
 * @作者：xie
 * @时间：2023/5/2 9:58
 */
public interface IPrintService {

    /**
     * 打印
     * @param printContent
     * @param machineCode
     */
    void print(PrintContent printContent, String machineCode);


}
