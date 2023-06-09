package com.restkeeper.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.DishCategory;

import java.util.List;
import java.util.Map;

/**
 * @作者：xie
 * @时间：2023/4/24 19:58
 */
public interface IDishCategoryService extends IService<DishCategory> {

    /**
     * 添加
     * @param name
     * @param type
     * @return
     */
    boolean add(String name, int type);

    /**
     * 修改
     * @param id
     * @param categoryName
     * @return
     */
    boolean update(String id, String categoryName);


    /**
     * 分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<DishCategory> queryPage(int pageNum, int pageSize);

    /**
     * 根据分类获取下拉列表
     * @param type
     * @return
     */
    List<Map<String,Object>> findCategoryList(Integer type);
}
