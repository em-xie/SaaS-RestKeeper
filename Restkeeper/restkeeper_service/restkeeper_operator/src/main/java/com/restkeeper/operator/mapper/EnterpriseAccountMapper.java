package com.restkeeper.operator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.restkeeper.operator.entity.EnterpriseAccount;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @作者：xie
 * @时间：2023/4/23 9:31
 */
public interface EnterpriseAccountMapper extends BaseMapper<EnterpriseAccount> {


    /**
     * 数据还原
     * @param id
     * @return
     */
    @Update("update t_enterprise_account set is_deleted=0 where enterprise_id=#{id} and is_deleted=1")
    boolean recovery(@Param("id") String id);
}
