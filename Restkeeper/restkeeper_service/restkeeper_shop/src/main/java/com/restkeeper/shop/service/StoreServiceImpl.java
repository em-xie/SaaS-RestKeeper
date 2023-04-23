package com.restkeeper.shop.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.shop.entity.Store;
import com.restkeeper.shop.mapper.StoreMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;

@Service(version = "1.0.0",protocol = "dubbo")
@Slf4j
public class StoreServiceImpl extends ServiceImpl<StoreMapper, Store> implements IStoreService {

    @Override
    public IPage<Store> queryPageByName(int pageNo, int pageSize, String name) {


        IPage<Store> page = new Page<>(pageNo,pageSize);

        QueryWrapper<Store> queryWrapper = new QueryWrapper<>();

        if (StringUtils.isNotEmpty(name)){
            queryWrapper.lambda().like(Store::getStoreName,name);
        }
        return this.page(page,queryWrapper);
    }
}
