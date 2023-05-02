package com.restkeeper.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.response.vo.PageVO;
import com.restkeeper.store.entity.Dish;
import com.restkeeper.store.entity.DishFlavor;
import com.restkeeper.store.entity.SetMeal;
import com.restkeeper.store.service.*;
import com.restkeeper.vo.DishFlavorVO;
import com.restkeeper.vo.DishVO;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * @作者：xie
 * @时间：2023/5/2 14:20
 */
@RestController
@RequestMapping("/dish")
public class DishController {

    @Reference(version = "1.0.0",check = false)
    private IDishCategoryService dishCategoryService;

    @Reference(version = "1.0.0",check = false)
    private IDishService dishService;

    @Reference(version = "1.0.0",check = false)
    private ISellCalculationService sellCalculationService;

    @Reference(version = "1.0.0",check = false)
    private ISetMealService setMealService;

    @Reference(version = "1.0.0",check = false)
    private IDishFlavorService dishFlavorService;

    //获取套餐和菜品的分类信息
    @GetMapping("/category")
    public List<Map<String,Object>> getCategory(){
        return dishCategoryService.findCategoryList(null);
    }

    //查询可用的菜品列表
    @GetMapping("/findEnableDishList/{categoryId}")
    public List<Map<String,Object>> findEnableDishList(@PathVariable String categoryId,
                                                       @RequestParam(value = "name",defaultValue = "",required = false) String name){
        return dishService.findEnableDishListInfo(categoryId, name);
    }

    //分页获取菜品列表信息
    @GetMapping("/dishPageList/{categoryId}/{type}/{page}/{pageSize}")
    public PageVO<DishVO> getDishByCategory(@PathVariable String categoryId,
                                            @PathVariable int type,
                                            @PathVariable long page,
                                            @PathVariable long pageSize){
        PageVO<DishVO> result = new PageVO<>();

        if (type == 1){
            //菜品
            IPage<Dish> dishPage = dishService.queryByCategoryId(categoryId,page,pageSize);
            result.setPages(dishPage.getPages());
            result.setPage(dishPage.getCurrent());
            result.setPagesize(dishPage.getSize());
            result.setCounts(dishPage.getTotal());
            result.setItems(dishPage.getRecords().stream().map(d->{

                DishVO dishVO = new DishVO();
                dishVO.setDishId(d.getId());
                dishVO.setDishName(d.getName());
                dishVO.setPrice(d.getPrice());
                dishVO.setType(1);
                dishVO.setDesc(d.getDescription());
                dishVO.setImageUrl(d.getImage());
                dishVO.setRemainder(sellCalculationService.getRemainderCount(d.getId()));
                return dishVO;

            }).collect(Collectors.toList()));

            return result;
        }else if (type == 2){
            //套餐
            IPage<SetMeal> dishPage =  setMealService.getByCategoryId(categoryId,page,pageSize);
            result.setPages(dishPage.getPages());
            result.setPage(dishPage.getCurrent());
            result.setPagesize(dishPage.getSize());
            result.setCounts(dishPage.getTotal());
            result.setItems(dishPage.getRecords().stream().map(d->{

                DishVO dishVO = new DishVO();
                dishVO.setDishId(d.getId());
                dishVO.setDishName(d.getName());
                dishVO.setPrice(d.getPrice());
                dishVO.setType(2);
                dishVO.setDesc(d.getDescription());
                dishVO.setImageUrl(d.getImage());
                dishVO.setRemainder(sellCalculationService.getRemainderCount(d.getId()));
                return dishVO;

            }).collect(Collectors.toList()));

            return result;
        }

        throw new BussinessException("菜品类别有误");
    }

    //根据菜品id获取口味信息
    @GetMapping("/flavor/{dishId}")
    public List<DishFlavorVO> dishFlavor(@PathVariable String dishId){

        List<DishFlavor> dishFlavors = dishFlavorService.getFlavor(dishId);

        List<DishFlavorVO> dishFlavorVOList =new ArrayList<>();

        dishFlavors.forEach(d->{
            DishFlavorVO dishFlavorVO = new DishFlavorVO();
            dishFlavorVO.setFlavor(d.getFlavorName());
            //[加酸, 加甜]
            String flavorValue = d.getFlavorValue();
            String substring = flavorValue.substring(flavorValue.indexOf("[") + 1, flavorValue.indexOf("]"));
            if (StringUtils.isNotEmpty(substring)){
                String[] split = substring.split(",");
                dishFlavorVO.setFlavorData(Arrays.asList(split));
            }
            dishFlavorVOList.add(dishFlavorVO);
        });

        return dishFlavorVOList;
    }
}
