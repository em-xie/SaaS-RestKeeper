package com.restkeeper.store.service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.store.entity.DishFlavor;
import com.restkeeper.store.mapper.DishFlavorMapper;
import org.apache.dubbo.config.annotation.Service;
/**
 * @作者：xie
 * @时间：2023/4/24 21:33
 */
@org.springframework.stereotype.Service("dishFlavorService")
@Service(version = "1.0.0",protocol = "dubbo")
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements IDishFlavorService {
}
