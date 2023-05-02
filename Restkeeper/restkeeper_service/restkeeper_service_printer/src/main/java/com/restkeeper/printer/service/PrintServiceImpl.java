
package com.restkeeper.printer.service;

import com.restkeeper.print.PrintContent;

import com.restkeeper.printer.config.Methods;
import com.restkeeper.printer.config.PrinterContentParser;
import com.restkeeper.service.IPrintService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Service(version = "1.0.0", protocol = "dubbo")
@Component
public class PrintServiceImpl implements IPrintService{


    @Override
    public void print(PrintContent printContent, String machineCode) {
        Methods.getInstance().getFreedomToken();
        String originId = System.nanoTime() + "";
        String result = Methods.getInstance().print(machineCode, PrinterContentParser.parser(printContent),originId);
        log.info("print service response:"+result);
    }
}