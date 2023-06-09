package com.restkeeper.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.dto.*;
import com.restkeeper.entity.OrderEntity;
import com.restkeeper.entity.ReverseOrder;

import java.time.LocalDate;
import java.util.List;

public interface IOrderService extends IService<OrderEntity> {
    //下单
    String addOrder(OrderEntity orderEntity);


    /**
     * 退菜
     * @param detailDTO
     * @return
     */
    public boolean returnDish(DetailDTO detailDTO);


    /**
     * 结账
     * @param orderEntity
     * @return
     */
    boolean pay(OrderEntity orderEntity);

    /**
     * 挂账
     * @param orderEntity
     * @param creditDTO
     * @return
     */
    boolean pay(OrderEntity orderEntity, CreditDTO creditDTO);

    boolean changeTable(String orderId,String targetTableId);

    CurrentAmountCollectDTO getCurrentCollect(LocalDate start, LocalDate end);

    /**
     * 统计24小时销售数据
     * @param start
     * @param end
     * @param type 统计类型 1:销售额;2:销售数量
     * @return
     */
    List<CurrentHourCollectDTO> getCurrentHourCollect(LocalDate start, LocalDate end, Integer type);



    List<PayTypeCollectDTO> getPayTypeCollect(LocalDate start, LocalDate end);

    /**
     * 获取当日优惠数据统计
     * @param start
     * @param end
     * @return
     */
    PrivilegeDTO getPrivilegeCollect(LocalDate start,LocalDate end);

    String addMicroOrder(OrderEntity orderEntity);
}
