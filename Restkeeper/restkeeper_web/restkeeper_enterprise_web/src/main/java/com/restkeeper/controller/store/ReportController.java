package com.restkeeper.controller.store;
import com.google.common.collect.Lists;
import com.restkeeper.dto.CurrentAmountCollectDTO;
import com.restkeeper.dto.CurrentHourCollectDTO;
import com.restkeeper.dto.PrivilegeDTO;
import com.restkeeper.service.IOrderDetailService;
import com.restkeeper.service.IOrderService;
import com.restkeeper.vo.store.AmountCollectVO;
import com.restkeeper.vo.store.BarChartCollectVO;
import com.restkeeper.vo.store.PieVo;
import com.restkeeper.vo.store.PrivilegeVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @作者：xie
 * @时间：2023/4/27 19:49
 */


@Api(tags = { "报表" })
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference(version = "1.0.0", check = false)
    private IOrderService orderService;

    @ApiOperation(value = "获取当日销量数据")
    @GetMapping("/amountCollect")
    public AmountCollectVO getAmountCollect() {
        AmountCollectVO vo = new AmountCollectVO();
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        CurrentAmountCollectDTO dto = orderService.getCurrentCollect(start, end);
        BeanUtils.copyProperties(dto, vo);

        return vo;
    }


    @ApiOperation(value = "获取当天24小时销量数据汇总")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", value = "类型(1:金额;2:数量)", required = true, dataType = "Int")})
    @GetMapping("/hourCollect/{type}")
    public BarChartCollectVO getHourCollect(@PathVariable Integer type){
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        List<CurrentHourCollectDTO> dtos = orderService.getCurrentHourCollect(start,end,type);
        BarChartCollectVO vo = new BarChartCollectVO();
        dtos.forEach(d->{
            vo.getXAxis().add(d.getCurrentDateHour() + "");
            vo.getSeries().add(d.getTotalAmount());
        });
        return vo;
    }

    @Reference(version = "1.0.0",check = false)
    private IOrderDetailService orderDetailService;

    @ApiOperation(value = "获取菜品分类销售排行")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "type", value = "类型(1:金额;2:数量)", required = true, dataType = "Int")})
    @GetMapping("/categoryCollect/{type}")
    public List<PieVo> getCategoryAmountCollect(@PathVariable int type){

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        if (type == 1){

            return orderDetailService.getCurrentCategoryAmountCollect(start,end).stream().map(d->{
                PieVo pieVo = new PieVo();
                pieVo.setValue(d.getDishAmount());
                pieVo.setName(d.getDishCategoryName());
                return pieVo;
            }).collect(Collectors.toList());
        }

        if (type == 2){

            return orderDetailService.getCurrentCategoryCountCollect(start,end).stream().map(d->{
                PieVo pieVo = new PieVo();
                pieVo.setValue(d.getTotalCount());
                pieVo.setName(d.getDishCategoryName());
                return pieVo;
            }).collect(Collectors.toList());
        }

        return null;
    }

    /**
     * 获取当日菜品销售排行
     * @return
     */
    @ApiOperation(value = "获取当日菜品销售排行")
    @GetMapping("/currentDishRank")
    public BarChartCollectVO getCurrentDishRank(){
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);

        BarChartCollectVO result = new BarChartCollectVO();
        orderDetailService.getCurrentDishRank(start,end)
                .forEach(d->{
                    result.getXAxis().add(d.getDishName());
                    result.getSeries().add(d.getTotalCount());
                });

        return result;
    }

    @ApiOperation(value = "获取各种支付类型数据汇总")
    @GetMapping("/payTypeCollect")
    public List<PieVo> getPayTypeCollect(){
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        return orderService.getPayTypeCollect(start,end)
                .stream()
                .map(d-> {
                    //通过map将DTO转换成PieVo
                    PieVo pieVo = new PieVo();
                    pieVo.setName(d.getPayName());
                    pieVo.setValue(d.getTotalCount());
                    return pieVo;
                })
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "获取各种优惠类型数据汇总")
    @GetMapping("/privilegeCollect")
    public PrivilegeVO getPrivilegeCollect(){

        PrivilegeVO privilegeVO = new PrivilegeVO();

        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(1);
        PrivilegeDTO privilegeDTO = orderService.getPrivilegeCollect(start, end);


        List<PieVo> pieVOList = Lists.newArrayList();

        double total = privilegeDTO.getPresentAmount()+privilegeDTO.getFreeAmount()+privilegeDTO.getSmallAmount();

        //赠菜
        PieVo present = new PieVo();
        present.setName("赠菜");
        present.setValue(privilegeDTO.getPresentAmount());
        present.setPercent(((double)privilegeDTO.getPresentAmount())/total * 100);
        pieVOList.add(present);

        //抹零
        PieVo small = new PieVo();
        small.setName("抹零");
        small.setValue(privilegeDTO.getSmallAmount());
        small.setPercent(((double)privilegeDTO.getSmallAmount())/total * 100);
        pieVOList.add(small);

        //免单
        PieVo free = new PieVo();
        free.setName("免单");
        free.setValue(privilegeDTO.getFreeAmount());
        free.setPercent(((double)privilegeDTO.getFreeAmount())/total * 100);
        pieVOList.add(free);

        privilegeVO.setDataList(pieVOList);
        privilegeVO.setTotal(privilegeDTO.getPresentAmount()+privilegeDTO.getFreeAmount()+privilegeDTO.getSmallAmount());

        return privilegeVO;
    }

}