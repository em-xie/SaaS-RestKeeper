package com.itheima.user.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.user.entity.User;
import com.itheima.user.service.IUserService;
import com.itheima.user.vo.PageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
* <p>
    *  前端控制器
    * </p>
*
* @author itheima
* @since 2020-03-10
* @version v1.0
*/
@Slf4j
    @Api(tags = {""})
    @RestController
@RequestMapping("/user/user")
        public class UserController {
    @Autowired
    private IUserService userService;

    /**
    * 查询分页数据
    */
    @ApiOperation(value = "查询分页数据")
    @ApiImplicitParams({
    @ApiImplicitParam(paramType="query", name = "page", value = "当前页码", required = false, dataType = "Integer"),
    @ApiImplicitParam(paramType="query", name = "pagesize", value = "分大小", required = false, dataType = "Integer"),
    @ApiImplicitParam(paramType="query", name = "searchMap", value = "任意参数组合", required = false, dataType = "Map")
    })
    @PostMapping(value = "/pagelist")
    public PageVO<User> findListByPage(@RequestParam(name = "page", defaultValue = "1") int pageNum,@RequestParam(name = "pagesize", defaultValue = "20") int pageSize, @RequestParam Map searchMap){
    IPage<User> page = new Page<User>(pageNum, pageSize);
    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
    if(searchMap!=null&!searchMap.isEmpty()) {
    queryWrapper.allEq(searchMap);
    }
    return new PageVO<User>(userService.page(page,queryWrapper));
    }


    @ApiOperation(value = "查询数据(不分页)")
    @ApiImplicitParam(paramType="query", name = "searchMap", value = "任意参数组合", required = false, dataType = "Map")
    @PostMapping("/list")
    @ResponseBody
    public List<User> list(@RequestParam Map searchMap) {
    QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
    if(searchMap!=null&!searchMap.isEmpty()) {
    queryWrapper.allEq(searchMap);
    }
    return userService.list(queryWrapper);
    }


    /**
    * 根据id查询
    */
    @ApiOperation(value = "根据id查询数据")
    @ApiImplicitParam(paramType="query", name = "pkid", value = "主键", required = true, dataType = "String")
    @PostMapping(value = "/getById")
    public User getById(@RequestParam("pkid") String pkid){
        return userService.getById(pkid);
    }

    /**
    * 新增
    */
    @ApiOperation(value = "新增数据")
    @ApiImplicitParam(paramType="body", name = "user", value = "json数据", required = true, dataType = "User")
    @PostMapping(value = "/add")
    public boolean add(@RequestBody User user){
    return userService.save(user);
    }

    /**
    * 删除
    */
    @ApiOperation(value = "删除数据")
    @ApiImplicitParam(paramType="query", name = "ids", value = "支持主键批量删除", required = true, dataType = "List")
    @PostMapping(value = "/del")
    public boolean delete(@RequestParam("ids") List<String> ids){
    return userService.removeByIds(ids);
    }

    /**
    * 修改
    */
    @ApiOperation(value = "更新数据")
    @ApiImplicitParam(paramType="body", name = "user", value = "json数据", required = true, dataType = "User")
    @PostMapping(value = "/update")
    public boolean update(@RequestBody User user){
    return userService.updateById(user);
    }

    }
