package com.restkeeper.print;

import com.alibaba.fastjson.JSON;
import com.restkeeper.constants.SystemCode;
import com.restkeeper.store.service.IPrinterService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PrintListener {

    @Reference(version = "1.0.0",check = false)
    private IPrinterService printerService;

    @RabbitListener(queues = SystemCode.PRINTER_QUEUE_NAME)
    public void receivePrintMessage(String message){

        //转换消息
        PrintMessage printMessage = JSON.parseObject(message, PrintMessage.class);

        //执行打印
        if (printMessage.isFront()){
            //前台收银区打印
            printerService.frontPrinter(printMessage.getOrderId(),printMessage.getPrintType(),printMessage.getStoreId(),printMessage.getShopId());

        }else {
            //后厨打印
            printerService.backPrinter(printMessage.getDishId(),printMessage.getPrintType(),printMessage.getDishNumber(),printMessage.getStoreId(),printMessage.getShopId());

        }
    }
}
