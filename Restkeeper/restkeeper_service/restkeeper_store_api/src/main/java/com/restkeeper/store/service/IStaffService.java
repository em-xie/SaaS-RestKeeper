package com.restkeeper.store.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.Staff;
import com.restkeeper.utils.Result;

public interface IStaffService extends IService<Staff> {

    //新增员工信息
    boolean addStaff(Staff staff);

    /**
     * <p>
     * 员工信息 服务类
     * </p>
     */
    Result login(String shopId, String loginName, String loginPass);
}
