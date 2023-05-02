package com.restkeeper.store.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.store.entity.DishFlavor;
import com.restkeeper.store.mapper.DishFlavorMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @作者：xie
 * @时间：2023/4/24 21:33
 */
@org.springframework.stereotype.Service("dishFlavorService")
@Service(version = "1.0.0",protocol = "dubbo")
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements IDishFlavorService {
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Override
    public List<DishFlavor> getFlavor(String dishId) {
        return dishFlavorMapper.selectFlavors(dishId);
    }
}
