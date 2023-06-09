package com.restkeeper.controller;
import com.restkeeper.store.service.IStaffService;
import com.restkeeper.utils.Result;
import com.restkeeper.vo.LoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @作者：xie
 * @时间：2023/4/26 9:22
 */
@Api(tags = {"收银端登录接口"})
@RestController
public class LoginController {
    @Reference(version = "1.0.0",check = false)
    private IStaffService staffService;

    @ApiOperation(value = "登录校验")
    @ApiImplicitParam(name = "Authorization", value = "jwt token",
            required = false, dataType = "String",paramType="header")
    @PostMapping("/login")
    public Result login(@RequestBody LoginVO loginVO){
        return staffService.login(loginVO.getShopId(),loginVO.getLoginName(), loginVO.getLoginPass());
    }
}
