package com.restkeeper.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.entity.OrderEntity;
import com.restkeeper.order.mapper.OrderMapper;
import com.restkeeper.service.IOrderService;
import com.restkeeper.store.service.ISellCalculationService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@org.springframework.stereotype.Service("orderService")
@Service(version = "1.0.0",protocol = "dubbo")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderEntity> implements IOrderService {

    @Autowired
    @Qualifier("orderDetailService")
    private IOrderDetailService orderDetailService;

    @Reference(version = "1.0.0",check = false)
    private ISellCalculationService sellCalculationService;

    @Override
    @Transactional
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
}