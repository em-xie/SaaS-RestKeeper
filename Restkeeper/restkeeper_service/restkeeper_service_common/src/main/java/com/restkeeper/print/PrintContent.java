package com.restkeeper.print;

import lombok.Data;

import java.util.List;

@Data
public class PrintContent {

    /**
     * 打印份数
     */
    private int copies = 1;
    /**
     * 小票标题
     */
    private String title;
    /**
     * 打印流水号
     */
    private String serialNumber;
    /**
     * 打印流式内容
     */
    private List<String> streamContent;

    /**
     * 打印表格
     */
    private PrinterTable printerTable;

    /**
     * 下部简单内容
     */
    private List<String> printSimpleContent;

    /**
     * 底部内容
     */
    private String printBottom;
}
