package com.restkeeper.store.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.restkeeper.store.entity.CreditLogs;

import java.time.LocalDateTime;
import java.util.List;

public interface ICreditLogService extends IService<CreditLogs> {
    IPage<CreditLogs> queryPage(String creditId, int pageNum, int pageSize);


    /**
     * 导出使用，根据时间查询挂账记录
     * @param creditId
     * @param start
     * @param end
     * @return
     */
    List<CreditLogs> list(String creditId, LocalDateTime start, LocalDateTime end);
}
