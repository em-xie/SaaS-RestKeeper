package com.restkeeper.service;

/**
 * @作者：xie
 * @时间：2023/5/3 9:52
 */
public interface IWeiXinPay
{
    /**
     * 通过jsCode获取openId
     * @param jsCode
     * @return
     */
    String getOpenId(String jsCode);

    /**
     * 调用统一下单接口发起支付
     * @param openId
     * @param orderId
     * @return
     */
    String requestPay(String openId,String orderId);

    String notify(String xml) throws Exception;
}