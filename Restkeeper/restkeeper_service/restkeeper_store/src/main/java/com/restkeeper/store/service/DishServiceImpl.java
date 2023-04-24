package com.restkeeper.store.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.constants.SystemCode;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.DishFlavor;
import com.restkeeper.store.mapper.DishMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
/**
 * @作者：xie
 * @时间：2023/4/24 21:30
 */
@org.springframework.stereotype.Service("dishService")
@Service(version = "1.0.0",protocol = "dubbo")
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements IDishService{

    @Autowired
    @Qualifier("dishFlavorService")
    private IDishFlavorService dishFlavorService;

    @Override
    @Transactional
    public boolean save(Dish dish, List<DishFlavor> dishFlavorList) {
        try{
            //保存菜品
            this.save(dish);

            //保存口味
            dishFlavorList.forEach((dishFlavor)->{
                dishFlavor.setDishId(dish.getId());
            });
            dishFlavorService.saveBatch(dishFlavorList);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean update(Dish dish, List<DishFlavor> flavorList) {
        this.updateById(dish);
        //删除原来口味关系
        QueryWrapper<DishFlavor> dishFlavorQueryWrapper =new QueryWrapper<>();
        dishFlavorQueryWrapper.lambda().eq(DishFlavor::getDishId,dish.getId());
        dishFlavorService.remove(dishFlavorQueryWrapper);
        //建立新的口味关系
        flavorList.forEach(flavor->{
            flavor.setDishId(dish.getId());
        });
        return dishFlavorService.saveBatch(flavorList);
    }

    @Override
    public List<Map<String, Object>> findEnableDishListInfo(String categoryId, String name) {
        return null;
    }
}
