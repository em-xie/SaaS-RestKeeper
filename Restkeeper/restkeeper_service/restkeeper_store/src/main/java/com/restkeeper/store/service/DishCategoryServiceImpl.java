package com.restkeeper.store.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.DishCategory;
import com.restkeeper.store.mapper.DishCategoryMapper;
import com.restkeeper.store.mapper.DishMapper;
import org.apache.dubbo.config.annotation.Service;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @作者：xie
 * @时间：2023/4/24 20:02
 */

@Component
@Service(version = "1.0.0",protocol = "dubbo")
public class DishCategoryServiceImpl extends ServiceImpl<DishCategoryMapper, DishCategory> implements IDishCategoryService {


    @Override
    @Transactional
    public boolean add(String name, int type) {
        checkNameExit(name);
        DishCategory dishCategory = new DishCategory();
        dishCategory.setName(name);
        dishCategory.setTorder(0);
        dishCategory.setType(type);
        return this.save(dishCategory);

    }

    @Override
    @Transactional
    public boolean update(String id, String categoryName) {
        checkNameExit(categoryName);
        UpdateWrapper<DishCategory> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(DishCategory::getCategoryId,id)
                .set(DishCategory::getName,categoryName);
        return this.update(updateWrapper);
    }

    @Override
    public IPage<DishCategory> queryPage(int pageNum, int pageSize) {
        IPage<DishCategory> dishCategoryPage = new Page<>(pageNum,pageSize);
        QueryWrapper<DishCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .orderByDesc(DishCategory::getLastUpdateTime);
        return this.page(dishCategoryPage);

    }

    @Override
    public List<Map<String, Object>> findCategoryList(Integer type) {
        QueryWrapper<DishCategory> queryWrapper = new QueryWrapper<>();
        if(type != null)
        {
            queryWrapper.lambda().eq(DishCategory::getType,type);

        }
        queryWrapper.lambda().select(DishCategory::getCategoryId,DishCategory::getName);
        return this.listMaps(queryWrapper);
    }

    private void checkNameExit(String name) {
        QueryWrapper<DishCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().select(DishCategory::getCategoryId).eq(DishCategory::getName,name);
        Integer count = this.getBaseMapper().selectCount(queryWrapper);
        if(count>0)  throw new BussinessException("该分类名称已存在");
    }
}
