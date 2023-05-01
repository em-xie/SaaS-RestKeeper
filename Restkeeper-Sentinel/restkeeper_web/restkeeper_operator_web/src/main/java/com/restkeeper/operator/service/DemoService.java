package com.restkeeper.operator.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.restkeeper.operator.entity.OperatorUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class DemoService {

    @Reference(version = "1.0.0",check = false)
    private IOperatorUserService operatorUserService;

    @SentinelResource(value = "findList",blockHandler = "findListBlockHandler")
    public List<OperatorUser> findList(){
        return operatorUserService.findListInfo();
    }

    public List<OperatorUser> findListBlockHandler(BlockException e){
        log.info("访问被限制了");
        return new ArrayList<>();
    }
}
