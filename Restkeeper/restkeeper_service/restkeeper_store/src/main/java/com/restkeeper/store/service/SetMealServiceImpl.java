package com.restkeeper.store.service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.store.entity.SetMeal;
import com.restkeeper.store.entity.SetMealDish;
import com.restkeeper.store.mapper.SetMealMapper;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/**
 * @作者：xie
 * @时间：2023/4/24 21:59
 */
@Service(version = "1.0.0",protocol = "dubbo")
@org.springframework.stereotype.Service("setMealService")
public class SetMealServiceImpl extends ServiceImpl<SetMealMapper, SetMeal> implements ISetMealService {

    @Override
    public IPage<SetMeal> queryPage(int pageNum, int pageSize, String name) {

        IPage<SetMeal> page = new Page<>(pageNum,pageSize);

        QueryWrapper<SetMeal> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(name)){
            queryWrapper.lambda().like(SetMeal::getName,name);
        }
        return this.page(page,queryWrapper);
    }

    @Autowired
    @Qualifier("setMealDishService")
    private ISetMealDishService setMealDishService;

    @Override
    @Transactional
    public boolean add(SetMeal setMeal, List<SetMealDish> setMealDishes) {
        this.save(setMeal);
        setMealDishes.forEach(s->{
            s.setSetMealId(setMeal.getId());
            s.setIndex(0);
        });
        return setMealDishService.saveBatch(setMealDishes);
    }

    @Override
    @Transactional
    public boolean update(SetMeal setMeal, List<SetMealDish> setMealDishes) {

        try {
            //修改套餐基础信息
            this.updateById(setMeal);

            //删除原有的菜品关联关系
            if (setMealDishes != null || setMealDishes.size()>0){

                QueryWrapper<SetMealDish> queryWrapper =new QueryWrapper<>();
                queryWrapper.lambda().eq(SetMealDish::getSetMealId,setMeal.getId());
                setMealDishService.remove(queryWrapper);

                //重建菜品的关联关系
                setMealDishes.forEach((setMealDish)->{
                    setMealDish.setSetMealId(setMeal.getId());
                });

                setMealDishService.saveBatch(setMealDishes);
            }

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public IPage<SetMeal> getByCategoryId(String categoryId, long pageNo, long pageSize) {
        IPage<SetMeal> page = new Page<>(pageNo,pageSize);
        QueryWrapper<SetMeal> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(SetMeal::getCategoryId,categoryId);
        return this.page(page,queryWrapper);
    }
}