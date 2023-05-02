package com.restkeeper.print;

import com.google.common.collect.Lists;

import java.util.List;

public class PrinterRow {

    private List<PrinterColumn> columnList = Lists.newArrayList();

    public void addColumn(String content){
        PrinterColumn printerColumn = new PrinterColumn();
        printerColumn.setContent(content);
        columnList.add(printerColumn);
    }

    public List<PrinterColumn> getColumn(){
        return columnList;
    }
}
