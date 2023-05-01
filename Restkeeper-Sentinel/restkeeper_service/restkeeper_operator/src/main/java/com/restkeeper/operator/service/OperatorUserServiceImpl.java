package com.restkeeper.operator.service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.restkeeper.operator.entity.OperatorUser;
import com.restkeeper.operator.mapper.OperatorUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

import java.util.List;

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
@Slf4j
public class OperatorUserServiceImpl extends ServiceImpl<OperatorUserMapper, OperatorUser> implements IOperatorUserService{

    @Override
    public List<OperatorUser> findListInfo() {
        return this.list();
    }

    @Override
    public void degradeTest() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Override
    public String blackTest() {

        ContextUtil.enter("black","operator-web");

        try(Entry entry = SphU.entry("black")){
            ContextUtil.exit();
            return "正常访问";
        }catch (BlockException e){
            e.printStackTrace();
            ContextUtil.exit();
            return "访问被限制了";
        }


    }



}
