package com.restkeeper.operator.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.restkeeper.operator.entity.OperatorUser;
import com.restkeeper.operator.mapper.OperatorUserMapper;
import com.restkeeper.utils.JWTUtil;
import com.restkeeper.utils.MD5CryptUtil;
import com.restkeeper.utils.Result;
import com.restkeeper.utils.ResultCode;
import io.netty.util.internal.StringUtil;
import org.apache.commons.codec.digest.Md5Crypt;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;

//@Service("operatorUserService")
@Service(version = "1.0.0",protocol = "dubbo")
/**
 * dubbo中支持的协议
 * dubbo 默认
 * rmi
 * hessian
 * http
 * webservice
 * thrift
 * memcached
 * redis
 */

public class OperatorUserServiceImpl extends ServiceImpl<OperatorUserMapper, OperatorUser> implements IOperatorUserService{
    @Override
    public IPage<OperatorUser> queryPageByName(int pageNum, int pageSize, String name) {

        IPage<OperatorUser> page = new Page<>(pageNum,pageSize);
        QueryWrapper<OperatorUser> queryWrapper = null;
        queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(name)){
            queryWrapper.like("loginname",name);
        }
        return this.page(page,queryWrapper);
    }




    @Value("${gateway.secret}")
    private String secret;
    @Override
    public Result login(String loginName, String loginPass) {
        Result result = new Result();
        //参数校验
        if (StringUtils.isEmpty(loginName)){
            result.setStatus(ResultCode.error);
            result.setDesc("用户名为空");
            return result;
        }
        if (StringUtils.isEmpty(loginPass)){
            result.setStatus(ResultCode.error);
            result.setDesc("密码为空");
            return result;
        }

        //查询用户是否存在
        QueryWrapper<OperatorUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",loginName);
        OperatorUser user = this.getOne(queryWrapper);
        if (user == null){
            result.setStatus(ResultCode.error);
            result.setDesc("用户不存在");
            return result;
        }

        //比对密码
        String salts = MD5CryptUtil.getSalts(user.getLoginpass());
        if (!Md5Crypt.md5Crypt(loginPass.getBytes(),salts).equals(user.getLoginpass())){
            result.setStatus(ResultCode.error);
            result.setDesc("密码不正确");
            return result;
        }

        //生成jwt令牌
        Map<String,Object> tokenInfo = Maps.newHashMap();
        tokenInfo.put("loginName",user.getLoginname());
        String token = null;
        try{
            //JWTUtil 是restkeeper_common提供的工具类
            token = JWTUtil.createJWTByObj(tokenInfo,secret);
        }catch (Exception e){

            log.error("加密失败");
            System.out.println( e.getMessage());
            result.setStatus(ResultCode.error);
            result.setDesc("生成令牌失败");
            return result;
        }

        //返回结果
        result.setStatus(ResultCode.success);
        result.setDesc("ok");
        result.setData(user);
        result.setToken(token);
        return result;
    }
}
