package com.restkeeper.response.interceptor;

import com.alibaba.nacos.client.utils.StringUtils;
import com.restkeeper.tenant.TenantContext;
import com.restkeeper.utils.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @作者：xie
 * @时间：2023/4/23 20:07
 */
@Slf4j
@Component
public class WebHandlerInterceptor implements HandlerInterceptor {

    //handler执行之前
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String authorization = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(authorization)) {
            try {
                Map<String,Object> tokenMap  = JWTUtil.decode(authorization);
                TenantContext.addAttachments(tokenMap);
            } catch (Exception ex) {
                log.error("解析token出错");
                // return false;
            }
        }
        return true;
    }
}