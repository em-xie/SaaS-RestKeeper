package com.restkeeper.store.service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.DishFlavor;

import java.util.List;
import java.util.Map;
/**
 * @作者：xie
 * @时间：2023/4/24 21:29
 */
public interface IDishService extends IService<Dish> {

    //新增菜品
    boolean save(Dish dish, List<DishFlavor> dishFlavorList);

    //修改菜品
    boolean update(Dish dish, List<DishFlavor> dishFlavorList);

    //根据分类信息与菜品名称查询相关数据列表
    List<Map<String,Object>> findEnableDishListInfo(String categoryId,String name);

    IPage<Dish> queryByCategoryId(String categoryId, long page, long pageSize);
}