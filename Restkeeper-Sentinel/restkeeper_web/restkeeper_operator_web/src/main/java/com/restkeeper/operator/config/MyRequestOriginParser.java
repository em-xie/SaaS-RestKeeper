package com.restkeeper.operator.config;

import com.alibaba.csp.sentinel.adapter.servlet.callback.RequestOriginParser;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

//@Component
public class MyRequestOriginParser implements RequestOriginParser {

    @Override
    public String parseOrigin(HttpServletRequest request) {

        //从请求路径或者请求头上获取请求来源的信息
        //这里以请求路径获取为例
        String originValue = request.getParameter("origin");

        if (StringUtils.isEmpty(originValue)){
            throw new IllegalArgumentException("origin must be exist");
        }

        return originValue;
    }
}
