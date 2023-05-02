package com.restkeeper.print;

import com.google.common.collect.Lists;

import java.util.List;

public class PrinterTable {

    private List<PrinterRow> rows = Lists.newArrayList();

    public void addRow(PrinterRow row){
        rows.add(row);
    }

    public List<PrinterRow> getRows(){
        return rows;
    }
}
