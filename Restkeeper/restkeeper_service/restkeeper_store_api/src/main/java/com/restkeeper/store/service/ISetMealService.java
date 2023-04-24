package com.restkeeper.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.SetMeal;
import com.restkeeper.store.entity.SetMealDish;

import java.util.List;

/**
 * @作者：xie
 * @时间：2023/4/24 21:58
 */
public interface ISetMealService extends IService<SetMeal> {

    IPage<SetMeal> queryPage(int pageNum, int pageSize, String name);

    boolean add(SetMeal setmeal, List<SetMealDish> setMealDishes);

    //修改套餐
    boolean update(SetMeal setMeal, List<SetMealDish> setMealDishes);
}
