package com.restkeeper.operator.config;

import com.alibaba.csp.sentinel.command.handler.ModifyParamFlowRulesCommandHandler;
import com.alibaba.csp.sentinel.datasource.*;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
public class RulesFileData implements InitFunc {

    @Override
    public void init() throws Exception {

        //定义规则文件路径
        String flowRulePath = "D:\\workspace\\itcast\\flowRules.json";
        String degradeRulePath = "D:\\workspace\\itcast\\degradeRules.json";
        String systemRulePath = "D:\\workspace\\itcast\\systemRules.json";
        String authorityRulePath = "D:\\workspace\\itcast\\authorityRules.json";
        String paramRulePath = "D:\\workspace\\itcast\\paramRules.json";

        //生成文件
        this.createFile(flowRulePath);
        this.createFile(degradeRulePath);
        this.createFile(systemRulePath);
        this.createFile(authorityRulePath);
        this.createFile(paramRulePath);

        //流控规则
        //写数据源实现
        WritableDataSource<List<FlowRule>> flowRuleWDS = new FileWritableDataSource<List<FlowRule>>(flowRulePath,this::encodeJson);
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWDS);

        //读数据源实现
        ReadableDataSource<String,List<FlowRule>> flowRuleRDS = new FileRefreshableDataSource<List<FlowRule>>(flowRulePath,flowRuleListParser);
        FlowRuleManager.register2Property(flowRuleRDS.getProperty());


        //降级规则
        WritableDataSource<List<DegradeRule>> degreadeRuleWDS = new FileWritableDataSource<List<DegradeRule>>(degradeRulePath,this::encodeJson);
        WritableDataSourceRegistry.registerDegradeDataSource(degreadeRuleWDS);

        ReadableDataSource degradeRuleRDS = new FileRefreshableDataSource(degradeRulePath,degradeRuleParser);
        DegradeRuleManager.register2Property(degradeRuleRDS.getProperty());

        //系统规则
        WritableDataSource<List<SystemRule>> systemRuleWDS = new FileWritableDataSource<List<SystemRule>>(systemRulePath,this::encodeJson);
        WritableDataSourceRegistry.registerSystemDataSource(systemRuleWDS);

        ReadableDataSource systemRuleRDS = new FileRefreshableDataSource(systemRulePath,systemRuleParser);
        SystemRuleManager.register2Property(systemRuleRDS.getProperty());

        //授权规则
        WritableDataSource<List<AuthorityRule>> authorityRuleWDS = new FileWritableDataSource<List<AuthorityRule>>(authorityRulePath,this::encodeJson);
        WritableDataSourceRegistry.registerAuthorityDataSource(authorityRuleWDS);

        ReadableDataSource authorityRuleRDS = new FileRefreshableDataSource(authorityRulePath,authorityRuleParser);
        AuthorityRuleManager.register2Property(authorityRuleRDS.getProperty());

        //参数规则
        WritableDataSource<List<ParamFlowRule>> paramRuleWDS = new FileWritableDataSource<List<ParamFlowRule>>(paramRulePath,this::encodeJson);
        ModifyParamFlowRulesCommandHandler.setWritableDataSource(paramRuleWDS);

        ReadableDataSource paramRuleRDS = new FileRefreshableDataSource(paramRulePath,paramRuleParser);
        ParamFlowRuleManager.register2Property(paramRuleRDS.getProperty());

    }

    private Converter<String,List<FlowRule>> flowRuleListParser = source->JSON.parseObject(source,new TypeReference<List<FlowRule>>(){});


    private Converter<String,List<DegradeRule>> degradeRuleParser = source ->JSON.parseObject(
            source,
            new TypeReference<List<DegradeRule>>(){}
    );

    private Converter<String,List<SystemRule>> systemRuleParser = source ->JSON.parseObject(
            source,
            new TypeReference<List<SystemRule>>(){}
    );

    private Converter<String,List<AuthorityRule>> authorityRuleParser = source ->JSON.parseObject(
            source,
            new TypeReference<List<AuthorityRule>>(){}
    );

    private Converter<String,List<ParamFlowRule>> paramRuleParser = source ->JSON.parseObject(
            source,
            new TypeReference<List<ParamFlowRule>>(){}
    );

    private <T>String encodeJson(T t) {
        return JSON.toJSONString(t);
    }

    private void createFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()){
            file.createNewFile();
        }
    }
}
