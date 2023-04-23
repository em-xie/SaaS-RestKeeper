package com.restkeeper.dubbo;

import com.restkeeper.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

/**
 * @作者：xie
 * @时间：2023/4/23 21:03
 */
@Activate
@Slf4j
public class DubboConsumerContextFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RpcContext.getContext().setAttachment("shopId", TenantContext.getShopId());

        return invoker.invoke(invocation);
    }
}