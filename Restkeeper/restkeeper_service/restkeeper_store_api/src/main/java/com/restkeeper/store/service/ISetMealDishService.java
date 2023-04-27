package com.restkeeper.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.SetMealDish;

import java.util.List;

/**
 * @作者：xie
 * @时间：2023/4/24 22:03
 */
public interface ISetMealDishService extends IService<SetMealDish> {
    Integer getDishCopiesInSetMeal(String dishId, String setMealId);

    List<Dish> getAllDishBySetMealId(String setMealId);
}
