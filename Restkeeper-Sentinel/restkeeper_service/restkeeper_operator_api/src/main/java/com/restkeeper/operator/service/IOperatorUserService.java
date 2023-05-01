package com.restkeeper.operator.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.operator.entity.OperatorUser;

import java.util.List;

public interface IOperatorUserService extends IService<OperatorUser> {

    List<OperatorUser> findListInfo();

    void degradeTest() throws InterruptedException;

    String blackTest();
}
