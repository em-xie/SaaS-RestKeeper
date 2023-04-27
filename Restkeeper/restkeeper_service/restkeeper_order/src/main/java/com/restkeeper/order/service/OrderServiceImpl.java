package com.restkeeper.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.aop.TenantAnnotation;
import com.restkeeper.constants.OrderDetailType;
import com.restkeeper.constants.OrderPayType;
import com.restkeeper.constants.SystemCode;
import com.restkeeper.dto.CreditDTO;
import com.restkeeper.dto.DetailDTO;
import com.restkeeper.entity.OrderEntity;
import com.restkeeper.entity.ReverseOrder;
import com.restkeeper.order.mapper.OrderMapper;
import com.restkeeper.service.IOrderService;
import com.restkeeper.store.entity.*;
import com.restkeeper.store.service.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.dubbo.config.annotation.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.entity.OrderDetailEntity;
import com.restkeeper.entity.OrderEntity;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.order.mapper.OrderMapper;
import com.restkeeper.service.IOrderDetailService;
import com.restkeeper.service.IOrderService;

import com.restkeeper.tenant.TenantContext;
import com.restkeeper.utils.SequenceUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@org.springframework.stereotype.Service("orderService")
@Service(version = "1.0.0",protocol = "dubbo")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {


    @Qualifier("orderDetailService")
    private IOrderDetailService orderDetailService;

    @Reference(version = "1.0.0",check = false)
    private ITableService tableService;

    @Reference(version = "1.0.0",check = false)
    private ISellCalculationService sellCalculationService;

    @Override
//    @Transactional
    @GlobalTransactional
    public String addOrder(OrderEntity orderEntity) {

        //生成订单流水号
        if (StringUtils.isEmpty(orderEntity.getOrderNumber())){
            String storeId = RpcContext.getContext().getAttachment("storeId");
            orderEntity.setOrderNumber(SequenceUtils.getSequence(storeId));
        }
        this.saveOrUpdate(orderEntity);

        //操作订单详情
        List<OrderDetailEntity> orderDetailEntities = orderEntity.getOrderDetails();
        orderDetailEntities.forEach(orderDetailEntity -> {

            orderDetailEntity.setOrderId(orderEntity.getOrderId());
            orderDetailEntity.setOrderNumber(SequenceUtils.getSequenceWithPrefix(orderEntity.getOrderNumber()));

            //沽清检查
            //手动的向TenantContext中设置相关的租户信息
            TenantContext.addAttachment("shopId",RpcContext.getContext().getAttachment("shopId"));
            TenantContext.addAttachment("storeId",RpcContext.getContext().getAttachment("storeId"));

            Integer remainder = sellCalculationService.getRemainderCount(orderDetailEntity.getDishId());
            if (remainder != -1){
                if (remainder < orderDetailEntity.getDishNumber()){
                    throw new BussinessException(orderDetailEntity.getDishName()+"超过沽清设置数目");
                }
            }

            //沽清扣减
            sellCalculationService.decrease(orderDetailEntity.getDishId(),orderDetailEntity.getDishNumber());

        });
        orderDetailService.saveBatch(orderDetailEntities);


        return orderEntity.getOrderId();
    }

    @Override
    @Transactional
    @TenantAnnotation
    public boolean returnDish(DetailDTO detailDTO) {
        OrderDetailEntity detailEntity = orderDetailService.getById(detailDTO.getDetailId());
        Integer detailStatus = detailEntity.getDetailStatus();
        if (OrderDetailType.PLUS_DISH.getType() == detailStatus || OrderDetailType.NORMAL_DISH.getType() == detailStatus){
            if(detailEntity.getDishNumber()<=0){
                throw new BussinessException(detailEntity.getDishName() + "已经被退完");
            }
            //产生新的退菜详情
            OrderDetailEntity return_detailEntity=new OrderDetailEntity();
            BeanUtils.copyProperties(detailEntity,return_detailEntity);
            //去掉多余copy字段
            return_detailEntity.setDetailId(null);
            return_detailEntity.setShopId(null);
            return_detailEntity.setStoreId(null);

            return_detailEntity.setOrderNumber(SequenceUtils.getSequenceWithPrefix(detailEntity.getOrderNumber()));
            return_detailEntity.setDetailStatus(OrderDetailType.RETURN_DISH.getType());
            return_detailEntity.setDishNumber(1);
            return_detailEntity.setReturnRemark(detailDTO.getRemarks().toString());
            orderDetailService.save(return_detailEntity);

//修改当前被退菜品在订单明细中的原有记录
            detailEntity.setDishNumber(detailEntity.getDishNumber()-1);
            detailEntity.setDishAmount(detailEntity.getDishNumber()*detailEntity.getDishPrice());
            orderDetailService.updateById(detailEntity);

            //修改订单主表信息
            OrderEntity orderEntity = this.getById(detailEntity.getOrderId());
            orderEntity.setTotalAmount(orderEntity.getTotalAmount()-detailEntity.getDishPrice());
            this.updateById(orderEntity);


            //判断沽清
            Integer remainderCount = sellCalculationService.getRemainderCount(detailEntity.getDishId());
            if (remainderCount >0){

                //沽清中有该菜品
                //沽清数量+1
                sellCalculationService.add(detailEntity.getDishId(),1);

            }
        }else{
            throw new BussinessException("不支持退菜操作");
        }

        return true;
        }

    @Override
    @Transactional
    @TenantAnnotation
    public boolean pay(OrderEntity orderEntity) {
        //修改订单主表的信息
        this.updateById(orderEntity);
        //修改桌台的状态
        Table table = tableService.getById(orderEntity.getTableId());
        table.setStatus(SystemCode.TABLE_STATUS_FREE);
        tableService.updateById(table);

        return true;
    }

    @Reference(version = "1.0.0",check = false)
    private ICreditService creditService;

    @Reference(version = "1.0.0",check = false)
    private ICreditCompanyUserService creditCompanyUserService;

    @Reference(version = "1.0.0",check = false)
    private ICreditLogService creditLogService;


    @Override
    @Transactional
    @TenantAnnotation
    public boolean pay(OrderEntity orderEntity, CreditDTO creditDTO) {
        this.updateById(orderEntity);

        //设置挂账信息
        if (orderEntity.getPayType() == OrderPayType.CREDIT.getType()){

            String creditId = creditDTO.getCreditId();
            Credit credit = creditService.getById(creditId);

            //个人用户
            if (credit.getCreditType() == SystemCode.CREDIT_TYPE_USER){

                //判断挂账人信息是否正确
                if (!credit.getUserName().equals(creditDTO.getCreditUserName())){
                    throw new BussinessException("挂账人信息不同，不允许挂账");
                }

                credit.setCreditAmount(credit.getCreditAmount() + creditDTO.getCreditAmount());
                creditService.saveOrUpdate(credit);

            }

            //公司用户
            List<CreditCompanyUser> companyUsers = null;
            if (credit.getCreditType() == SystemCode.CREDIT_TYPE_COMPANY){

                /*QueryWrapper<CreditCompanyUser> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(CreditCompanyUser::getCreditId,creditId);
                List<CreditCompanyUser> list = creditCompanyUserService.list(queryWrapper);*/

                List<CreditCompanyUser> companyUserList = creditCompanyUserService.getInfoList(creditId);

                //判断当前挂账人在集合中是否存在
                Optional<CreditCompanyUser> resultInfo = companyUserList.stream().filter(user -> user.getUserName().equals(creditDTO.getCreditUserName())).findFirst();
                if (!resultInfo.isPresent()){
                    //不存在，不允许挂账
                    throw new BussinessException("当前用户不在该公司中，请联系管家端进行设置");
                }
                companyUsers=companyUserList;

                credit.setCreditAmount(credit.getCreditAmount() + creditDTO.getCreditAmount());
                creditService.saveOrUpdate(credit);
            }

            //挂账明细信息
            CreditLogs creditLogs = new CreditLogs();
            creditLogs.setCreditId(creditId);
            creditLogs.setOrderId(orderEntity.getOrderId());
            creditLogs.setType(credit.getCreditType());
            creditLogs.setCreditAmount(creditDTO.getCreditAmount());
            creditLogs.setOrderAmount(orderEntity.getTotalAmount());
            creditLogs.setReceivedAmount(orderEntity.getTotalAmount());
            creditLogs.setCreditAmount(creditDTO.getCreditAmount());

            if (credit.getCreditType() == SystemCode.CREDIT_TYPE_COMPANY){

                creditLogs.setUserName(creditDTO.getCreditUserName());
                creditLogs.setCompanyName(credit.getCompanyName());
                Optional<CreditCompanyUser> optional = companyUsers.stream()
                        .filter(user -> user.getUserName().equals(creditDTO.getCreditUserName())).findFirst();
                String phone = optional.get().getPhone();
                creditLogs.setPhone(phone);

            }else if (credit.getCreditType() == SystemCode.CREDIT_TYPE_USER){

                creditLogs.setUserName(creditDTO.getCreditUserName());
                creditLogs.setPhone(credit.getPhone());
            }
            creditLogService.save(creditLogs);

            //修改桌台状态为空闲
            Table table = tableService.getById(orderEntity.getTableId());
            table.setStatus(SystemCode.TABLE_STATUS_FREE);
            tableService.updateById(table);

        }
        return true;
    }

    @Reference(version = "1.0.0",check = false)
    private ITableLogService tableLogService;

    @Override
    @Transactional
    @TenantAnnotation
    public boolean changeTable(String orderId, String targetTableId) {

        String loginUserName = RpcContext.getContext().getAttachment("loginUserName");

        //获取目标桌台信息
        Table targetTable = tableService.getById(targetTableId);
        if (targetTable == null){
            throw new BussinessException("桌台不存在");
        }
        if (targetTable.getStatus() != SystemCode.TABLE_STATUS_FREE){
            throw new BussinessException("桌台处于非空闲状态，不能换桌");
        }

        //获取订单对象
        OrderEntity orderEntity = this.getById(orderId);

        //获取原有的桌台对象
        Table sourceTable = tableService.getById(orderEntity.getTableId());

        //将原有的桌台对象状态设置为空闲
        sourceTable.setStatus(SystemCode.TABLE_STATUS_FREE);
        tableService.updateById(sourceTable);

        //将目标桌台的状态设置为开桌
        targetTable.setStatus(SystemCode.TABLE_STATUS_OPENED);
        tableService.updateById(targetTable);

        //新增桌台日志信息
        TableLog tableLog =new TableLog();
        tableLog.setTableStatus(SystemCode.TABLE_STATUS_OPENED);
        tableLog.setCreateTime(LocalDateTime.now());
        tableLog.setTableId(targetTableId);
        tableLog.setUserNumbers(orderEntity.getPersonNumbers());
        tableLog.setUserId(loginUserName);
        tableLogService.save(tableLog);
//        int i=1/0;

        //重新设置订单与桌台的关联
        orderEntity.setTableId(targetTableId);

        return this.updateById(orderEntity);
    }


}