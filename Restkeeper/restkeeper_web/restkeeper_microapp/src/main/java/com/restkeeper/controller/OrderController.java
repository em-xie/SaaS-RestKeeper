package com.restkeeper.controller;
import com.alibaba.fastjson.JSON;
import com.restkeeper.constants.SystemCode;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.store.service.ISellCalculationService;
import com.restkeeper.vo.DishRequestVO;
import com.restkeeper.vo.DishShopCartVO;
import com.restkeeper.vo.ShopCartVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @作者：xie
 * @时间：2023/5/2 15:12
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Reference(version = "1.0.0",check = false)
    private ISellCalculationService sellCalculationService;

    //加菜
    @RequestMapping("/addDish")
    public boolean addDish(@RequestBody DishRequestVO dishRequestVO){

        //判断沽清
        if (dishRequestVO.getDishNumber() == 1){
            //加菜
            sellCalculationService.plusDish(dishRequestVO.getDishId());

        }else if (dishRequestVO.getDishNumber() == -1){
            //减菜
            sellCalculationService.reduceDish(dishRequestVO.getDishId());
        }else {
            throw new BussinessException("参数不合法");
        }

        //获取购物车信息
        ShopCartVO shopCartVO = getShopCartVO(dishRequestVO.getTableId());

        //判断菜品在购物车中是否存在
        DishShopCartVO dishShopCartVO = null;
        if (shopCartVO.getDishList() != null && shopCartVO.getDishList().size()>0){
            dishShopCartVO = shopCartVO.getDishList().stream().filter(d->d.getId().equals(dishRequestVO.getDishId())).findFirst().get();
        }

        //封装菜品信息
        if (dishShopCartVO != null){
            dishShopCartVO.setNumber(dishShopCartVO.getNumber()+dishRequestVO.getDishNumber());
        }else {
            dishShopCartVO = new DishShopCartVO();
            dishShopCartVO.setNumber(dishRequestVO.getDishNumber());
            dishShopCartVO.setId(dishRequestVO.getDishId());
            dishShopCartVO.setPrice(dishRequestVO.getPrice());
            dishShopCartVO.setName(dishRequestVO.getDishName());
            dishShopCartVO.setFlavorRemark(dishRequestVO.getFlavorRemark());
            if(dishRequestVO.getType() == SystemCode.DISH_TYPE_MORMAL){
                dishShopCartVO.setType(SystemCode.DISH_TYPE_MORMAL);
            }else if(dishRequestVO.getType() == SystemCode.DISH_TYPE_SETMEAL){
                dishShopCartVO.setType(SystemCode.DISH_TYPE_SETMEAL);
            }
            shopCartVO.getDishList().add(dishShopCartVO);
        }

        //更新缓存中的购物车信息
        String key = SystemCode.MIRCO_APP_SHOP_CART_PREFIX+dishRequestVO.getTableId();
        String json = JSON.toJSONString(shopCartVO);
        redisTemplate.opsForValue().set(key,json);

        //向消息队列发送购物车信息
        sendToMQ(dishRequestVO.getTableId(),json);
        return true;

    }
//
//    /**
//     * 减菜业务
//     * @param dishId
//     * @return
//     */
//    @PostMapping("/decreaseDish/{tableId}/{dishId}")
//    public boolean decreaseDish(@PathVariable String tableId,@PathVariable String dishId){
//        ShopCartVO shopCartVO = getShopCartVO(tableId);
//        Optional<DishShopCartVO> dishShopCartVO = shopCartVO.getDishList().stream().filter(d->d.getId().equals(dishId)).findFirst();
//        if (!dishShopCartVO.isPresent()) return true;
//        shopCartVO.getDishList().remove(dishShopCartVO.get());
//        String key = SystemCode.MIRCO_APP_SHOP_CART_PREFIX + tableId;
//        String json = JSON.toJSONString(shopCartVO);
//        redisTemplate.opsForValue().set(key,json);
//        //通过mq推送消息到客户端
//        sendToMQ(tableId,json);
//
//        return true;
//
//    }


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private void sendToMQ(String tableId,String jsonMsg){

        try {
            MessageProperties messageProperties = new MessageProperties();
            messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
            messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);

            Message message = new Message(jsonMsg.getBytes("UTF-8"),messageProperties);
            rabbitTemplate.send(SystemCode.MICROSOFT_EXCHANGE_NAME,tableId,message);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            log.error("发送点餐消息失败");
        }

    }

    /**
     * 获取购物车信息
     * @param tableId
     * @return
     */
    @GetMapping("/shopCart/{tableId}")
    public ShopCartVO getshopCart(@PathVariable String tableId){
        return getShopCartVO(tableId);
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ShopCartVO getShopCartVO(String tableId) {

        String key = SystemCode.MIRCO_APP_SHOP_CART_PREFIX+tableId;
        String cartJson = redisTemplate.opsForValue().get(key);
        ShopCartVO shopCartVO = null;
        if (StringUtils.isEmpty(cartJson)){
            shopCartVO = new ShopCartVO();
            List<DishShopCartVO> dishList = new ArrayList<>();
            shopCartVO.setDishList(dishList);
        }else {
            shopCartVO = JSON.parseObject(cartJson,ShopCartVO.class);
        }

        return shopCartVO;
    }
}
