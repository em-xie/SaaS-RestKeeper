package com.restkeeper.printer.config;

import com.google.common.base.Strings;
import com.restkeeper.print.PrintContent;

public class PrinterContentParser {

    /**
     * 将打印内容转换为打印机指令内容
     * @param content
     * @return
     */
    public static String parser(PrintContent content){
        StringBuilder sb = new StringBuilder();
        sb.append("<MN>");
        sb.append(content.getCopies());
        sb.append("</MN>");
        sb.append("<center>");
        sb.append(content.getTitle());
        sb.append("</center>");
        sb.append("\n");
        sb.append("\n");
        if (!Strings.isNullOrEmpty(content.getSerialNumber())){
            sb.append("<FS>");
            sb.append("<FB>");
            sb.append(content.getSerialNumber());
            sb.append("</FB>");
            sb.append("</FS>");
            sb.append("\n");
        }
        if(content.getStreamContent() != null){
            content.getStreamContent()
                    .forEach(c->{
                        sb.append(c);
                        sb.append("\n");
                    });
        }
        if(content.getPrinterTable() != null){
            sb.append("-----------------------------");
            sb.append("\n");
            sb.append("<table>");
            content.getPrinterTable()
                    .getRows()
                    .forEach(r->{
                        sb.append("<tr>");
                        r.getColumn().forEach(c->{
                            sb.append("<td>");
                            sb.append(c.getContent());
                            sb.append("</td>");
                        });
                        sb.append("</tr>");
                        sb.append("<tr><td></td><td></td><td></td></tr>");
                    });
            sb.append("</table>");
            sb.append("-----------------------------");
            sb.append("\n");
        }

        if(content.getPrintSimpleContent() != null){
            content.getPrintSimpleContent()
                    .forEach(c->{
                        sb.append(c);
                        sb.append("\n");
                    });
        }

        if(content.getPrintBottom() != null){
            sb.append("<FS2>");
            sb.append("<FB>");
            sb.append(content.getPrintBottom());
            sb.append("</FB>");
            sb.append("</FS2>");
        }

        return sb.toString();
    }
}
