package com.restkeeper.store.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.DishFlavor;

import java.util.List;

/**
 * @作者：xie
 * @时间：2023/4/24 21:32
 */


public interface IDishFlavorService extends IService<DishFlavor> {
    List<DishFlavor> getFlavor(String dishId);
}
