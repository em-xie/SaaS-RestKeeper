package com.restkeeper.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.restkeeper.entity.OrderDetailMealEntity;
import com.restkeeper.order.mapper.OrderDetailMealMapper;
import com.restkeeper.service.IOrderDetailMealService;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.SetMealDish;
import com.restkeeper.store.service.IDishService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Service(version = "1.0.0",protocol = "dubbo")
@org.springframework.stereotype.Service("orderDetailMealService")
public class OrderDetailMealServiceImpl extends ServiceImpl<OrderDetailMealMapper, OrderDetailMealEntity> implements IOrderDetailMealService {

}
