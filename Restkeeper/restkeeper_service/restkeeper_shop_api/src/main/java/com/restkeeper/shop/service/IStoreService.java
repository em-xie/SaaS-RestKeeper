package com.restkeeper.shop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.shop.entity.Store;

public interface IStoreService extends IService<Store> {

    //分页查询（根据门店名称进行模糊查询）
    IPage<Store> queryPageByName(int pageNo, int pageSize, String name);

}