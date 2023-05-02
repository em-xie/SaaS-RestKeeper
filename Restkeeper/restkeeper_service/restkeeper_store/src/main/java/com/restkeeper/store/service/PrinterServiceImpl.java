package com.restkeeper.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.restkeeper.constants.SystemCode;
import com.restkeeper.entity.OrderDetailEntity;
import com.restkeeper.entity.OrderEntity;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.print.PrintContent;
import com.restkeeper.print.PrinterRow;
import com.restkeeper.print.PrinterTable;
import com.restkeeper.service.IOrderDetailService;
import com.restkeeper.service.IOrderService;
import com.restkeeper.service.IPrintService;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.Printer;
import com.restkeeper.store.entity.PrinterDish;
import com.restkeeper.store.mapper.PrinterDishMapper;
import com.restkeeper.store.mapper.PrinterMapper;
import com.restkeeper.tenant.TenantContext;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service(version = "1.0.0",protocol = "dubbo")
public class PrinterServiceImpl extends ServiceImpl<PrinterMapper, Printer> implements IPrinterService {
    @Autowired
    private PrinterDishMapper printerDishMapper;
    @Override
    public boolean addFrontPrinter(Printer printer) {
        return this.save(printer);
    }

    @Override
    @Transactional
    public boolean addBackendPrinter(Printer printer, List<String> dishIdList) {
        this.save(printer);
        dishIdList.forEach(d->{
            PrinterDish printerDish = new PrinterDish();
            printerDish.setPrinterId(printer.getPrinterId());
            printerDish.setDishId(d);
            printerDishMapper.insert(printerDish);
        });

        return true;
    }



    @Reference(version = "1.0.0",check = false)
    private IOrderService orderService;

    @Reference(version = "1.0.0",check = false)
    private IOrderDetailService orderDetailService;

    @Reference(version = "1.0.0",check = false)
    private IPrintService printService;
    @Override
    public void frontPrinter(String orderId, int printType, String storeId, String shopId) {

        PrintContent printContent = new PrintContent();
        if(printType == SystemCode.PRINT_BEFOREHAND){
            printContent.setTitle("预结单");
        }else if(printType == SystemCode.PRINT_BILL){
            printContent.setTitle("结账单");
        }else if(printType == SystemCode.PRINT_CUSTOMER){
            printContent.setTitle("客单");
        }

        //设置打印份数
        RpcContext.getContext().setAttachment("storeId",storeId);
        RpcContext.getContext().setAttachment("shopId",shopId);
        QueryWrapper<Printer> printQueryWrapper = new QueryWrapper<>();
        printQueryWrapper.lambda().eq(Printer::getAreaType,2);
        Printer printer = this.getOne(printQueryWrapper);
        printContent.setCopies(printer.getPrinterNumber());

        //设置流水号
        TenantContext.addAttachment("storeId",storeId);
        TenantContext.addAttachment("shopId",shopId);
        OrderEntity orderEntity = orderService.getById(orderId);
        printContent.setSerialNumber("流水号："+orderEntity.getOrderId());

        //设置流水内容
        List<String> streamContent = Lists.newArrayList();
        streamContent.add("账单号: "+orderId);
        streamContent.add("开台时间: "+orderEntity.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        if (printType == SystemCode.PRINT_BILL){
            streamContent.add("结账时间："+orderEntity.getLastUpdateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
        printContent.setStreamContent(streamContent);

        //设置详情信息
        PrinterTable printerTable = new PrinterTable();
        PrinterRow printerRow = new PrinterRow();
        printerRow.addColumn("品名");
        printerRow.addColumn("单价");
        printerRow.addColumn("数量");
        printerRow.addColumn("总额");
        printerTable.addRow(printerRow);

        TenantContext.addAttachment("storeId",storeId);
        TenantContext.addAttachment("shopId",shopId);

        AtomicInteger totalPrice = new AtomicInteger();

        List<OrderDetailEntity> orderDetailEntityList = orderDetailService.getOrderDetailListByOrderId(orderId);
        orderDetailEntityList.forEach(o->{
            PrinterRow row = new PrinterRow();
            row.addColumn(o.getDishName());

            totalPrice.set(totalPrice.get()+o.getDishPrice());
            float price = ((float)o.getDishPrice())/100;
            row.addColumn(price+"");
            row.addColumn(o.getDishNumber()+"");
            float amount = ((float)o.getDishAmount())/100;
            row.addColumn(amount+"");
            printerTable.addRow(row);
        });
        printContent.setPrinterTable(printerTable);

        List<String> simpleContent = Lists.newArrayList();
        simpleContent.add("原价合计:"+((float)totalPrice.get())/100);
        printContent.setPrintSimpleContent(simpleContent);
        printContent.setPrintBottom("实收金额: "+((float)orderEntity.getPayAmount()/100));

        //执行打印
        printService.print(printContent,printer.getMachineCode());

    }

    @Reference(version = "1.0.0",check = false)
    private IDishService dishService;

    @Override
    public void backPrinter(String dishId, int printType, int dishNumber, String storeId, String shopId) {

        //获取打印机关联的菜品信息
        RpcContext.getContext().setAttachment("storeId",storeId);
        RpcContext.getContext().setAttachment("shopId",shopId);
        QueryWrapper<PrinterDish> qw = new QueryWrapper<>();
        qw.lambda().eq(PrinterDish::getDishId,dishId);
        PrinterDish printerDish = printerDishMapper.selectOne(qw);
        if (printerDish == null) return;

        //获取菜品信息
        TenantContext.addAttachment("storeId",storeId);
        TenantContext.addAttachment("shopId",shopId);
        Dish dish = dishService.getById(dishId);

        //封装数据内容
        PrintContent printContent = new PrintContent();
        if(printType == SystemCode.PRINT_MADE_MENU){
            printContent.setTitle("制作菜单");
        }else if(printType == SystemCode.PRINT_RETURN_DISH){
            printContent.setTitle("退菜单");
        }else if(printType == SystemCode.PRINT_CHANGE_MENU){
            printContent.setTitle("转菜单");
        }else if(printType == SystemCode.PRINT_CHANGE_TABLE){
            printContent.setTitle("转台单");
        }

        List<String> contents = Lists.newArrayList();
        contents.add(dish.getName()+" "+dishNumber+"份");
        printContent.setStreamContent(contents);

        RpcContext.getContext().setAttachment("storeId",storeId);
        RpcContext.getContext().setAttachment("shopId",shopId);
        Printer printer = this.getById(printerDish.getPrinterId());
        printContent.setCopies(printer.getPrinterNumber());

        //执行打印
        printService.print(printContent,printer.getMachineCode());

    }
}
