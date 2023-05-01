package com.restkeeper.operator.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.context.ContextUtil;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.restkeeper.operator.entity.OperatorUser;
import com.restkeeper.operator.service.DemoService;
import com.restkeeper.operator.service.IOperatorUserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 管理员的登录接口
 */
@RestController
@RefreshScope //配置中心的自动刷新
@Slf4j
@Api(tags = {"管理员相关接口"})
public class UserController{


    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/echo")
    @SentinelResource(value = "echo",blockHandler = "flowBlockHandler")
    public String echo() {

        //调用规则
        //this.initFlowRule();

        //定义资源
        //被保护的业务代码
        return "i am from port: " + port;

    }

    //必须与原方法处于同一个类中
    //访问类型必须是public
    //返回值类型必须与原方法相同
    //形参必须与原方法的形参相同
    public String flowBlockHandler(BlockException e){
        e.printStackTrace();
        return "当前访问被限制";
    }

    //定义规则
    /*private void initFlowRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        //关联域名
        rule.setResource("echo");
        //设定限流阈值类型
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //设置限流阈值
        rule.setCount(2);
        //设置流控针对的调用来源，default为不区分来源
        rule.setLimitApp("default");
        rules.add(rule);
        FlowRuleManager.loadRules(rules);
    }*/


    @GetMapping("/info")
    @SentinelResource(value = "info",blockHandler = "infoFlowBlockHandler")
    public String info(){
        int i = 1/0;
        return "i am from port: " + port;
    }

    public String infoFlowBlockHandler(BlockException e){
        return "当前访问被限制了";
    }

    @Autowired
    private DemoService demoService;


    @GetMapping("/demoA")
    public List<OperatorUser> demoA(){
        //声明入口资源
        ContextUtil.enter("demoA");
        return demoService.findList();
    }

    @GetMapping("/demoB")
    public List<OperatorUser> demoB(){
        //声明入口资源
        ContextUtil.enter("demoB");
        return demoService.findList();
    }

    @Reference(version = "1.0.0",check = false)
    private IOperatorUserService operatorUserService;

    @GetMapping("/degradeTest")
    @SentinelResource(value = "degradeTest",blockHandler = "degradeTestHandler")
    public String degradeTest() throws InterruptedException {
        operatorUserService.degradeTest();
        return "正常访问";
    }

    public String degradeTestHandler(BlockException e){
        return "当前访问被限制了，需要等待一定的时长后再来访问";
    }

    @GetMapping("/hot")
    @SentinelResource(value = "hot",blockHandler = "hotHandler",fallback = "fallbackHandler")
    public String hot(@RequestParam(required = false) String id , @RequestParam(required = false) String name){

        if ("lisi".equals(name)){
            throw new RuntimeException("name is bad");
        }

        return "id:  "+id+" . name: "+name+"";
    }

    public String fallbackHandler(String id,String name){
        log.error("fallbackHandler have execute");
        return "fallbackHandler have execute";
    }


    public String hotHandler(String id,String name,BlockException e){
        return "热点参数限流了";
    }

    @GetMapping("/blackTest")
    public String blackTest(){
        return operatorUserService.blackTest();
    }




}
