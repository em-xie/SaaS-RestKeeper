package com.restkeeper.controller.store;
import com.restkeeper.store.entity.Printer;
import com.restkeeper.store.service.IPrinterService;
import com.restkeeper.vo.store.PrinterVO;
import io.swagger.annotations.Api;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;
/**
 * @作者：xie
 * @时间：2023/5/1 19:47
 */
@Api(tags = {"打印接口"})
@RestController
@RequestMapping("/printer")
public class PrinterController {

    @Reference(version = "1.0.0", check=false)
    private IPrinterService printerService;

    @PostMapping
    public boolean addPrinter(@RequestBody PrinterVO printerVO){

        Printer printer = new Printer();
        printer.setAreaType(printerVO.getAreaType());
        printer.setEnableBeforehand(printerVO.isEnableBeforehand());
        printer.setEnableBill(printerVO.isEnableBill());
        printer.setEnableChangeMenu(printerVO.isEnableChangeMenu());
        printer.setEnableChangeTable(printerVO.isEnableChangeTable());
        printer.setEnableCustomer(printerVO.isEnableCustomer());
        printer.setEnableMadeMenu(printerVO.isEnableMadeMenu());
        printer.setEnableReturnDish(printerVO.isEnableReturnDish());
        printer.setMachineCode(printerVO.getMachineCode());
        printer.setPrinterName(printerVO.getPrinterName());

        if(printerVO.getAreaType() == 1){
            return printerService.addBackendPrinter(printer,printerVO.getDishIdList());
        }else {
            return printerService.addFrontPrinter(printer);
        }
    }
}

