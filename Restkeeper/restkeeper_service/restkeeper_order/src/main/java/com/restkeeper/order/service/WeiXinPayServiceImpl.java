package com.restkeeper.order.service;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayRequest;
import com.github.wxpay.sdk.WXPayUtil;
import com.github.wxpay.sdk.WxPaySdkConfig;
import com.restkeeper.constants.SystemCode;
import com.restkeeper.entity.OrderEntity;
import com.restkeeper.exception.BussinessException;
import com.restkeeper.lock.CalculationBusinessLock;
import com.restkeeper.order.weixin.WXConfig;
import com.restkeeper.service.IOrderService;
import com.restkeeper.service.IWeiXinPay;
import com.restkeeper.tenant.TenantContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service(version = "1.0.0", protocol = "dubbo")
@Component
public class WeiXinPayServiceImpl implements IWeiXinPay {

    @Autowired
    private WXConfig wxConfig;


    @Override
    public String getOpenId(String jscode) {

        String getOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid="+wxConfig.getAppId()+"&secret="+wxConfig.getAppSecret()+"&js_code="+jscode+"&grant_type=authorization_code";
        RestTemplate restTemplate = new RestTemplate();
        String respResult =  restTemplate.getForObject(getOpenIdUrl,String.class);

        if (StringUtils.isEmpty(respResult)) return null;

        try{

            String errCodeValue =  JSON.parseObject(respResult).getString("errcode");

            if (errCodeValue != null){
                int errCodeInt = JSON.parseObject(respResult).getInteger("errcode");
                if (errCodeInt != 0) return null;
            }

            return JSON.parseObject(respResult).getString("openid");
        }catch (Exception e){
            return null;
        }
    }

    @Autowired
    private IOrderService orderService;

    @Autowired
    private WxPaySdkConfig wxPaySDKConfig;


    @Override
    @Transactional
    public String requestPay(String orderId, String openId) {

        OrderEntity orderEntity = orderService.getById(orderId);

        try{
            //封装请求参数
            Map<String,String> map = new HashMap<>();
            map.put("appid",wxConfig.getAppId());
            map.put("mch_id",wxConfig.getMchId());
            String nonceStr = WXPayUtil.generateNonceStr();
            map.put("nonce_str",nonceStr);
            map.put("body","餐掌柜点餐支付");
            map.put("out_trade_no",orderId);
            map.put("total_fee",orderEntity.getTotalAmount()+"");
            map.put("spbill_create_ip","127.0.0.1");
            map.put("notify_url",wxConfig.getNotifyUrl());
            map.put("trade_type","JSAPI");
            map.put("openid",openId);

            String xmlParam = WXPayUtil.generateSignedXml(map, wxConfig.getPartnerKey());

            //修改订单支付状态
            orderEntity.setPayStatus(SystemCode.ORDER_STATUS_PAYING);
            orderService.updateById(orderEntity);

            //调用统一下单的api
            WXPayRequest wxPayRequest = new WXPayRequest(wxPaySDKConfig);
            String xmlResult = wxPayRequest.requestWithCert("/pay/unifiedorder",null,xmlParam,false);

            //解析返回结果
            Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);

            Map<String,String> response = new HashMap<>();
            //状态码
            String return_code = mapResult.get("return_code");

            if ("SUCCESS".equals(return_code)){

                //获取与支付订单id
                String prepay_id = mapResult.get("prepay_id");
                if (StringUtils.isEmpty(prepay_id)){
                    throw new BussinessException("当前订单可能已经被其他人支付");
                }

                //再次签名
                response.put("appId",wxConfig.getAppId());
                Long timeStamp = System.currentTimeMillis() /1000;
                response.put("timeStamp",timeStamp+"");
                response.put("nonceStr",WXPayUtil.generateNonceStr());
                response.put("package","prepay_id="+prepay_id);
                response.put("signType","MD5");
                String sign = WXPayUtil.generateSignature(response,wxConfig.getPartnerKey());

                response.put("paySign",sign);
                response.put("appId","");
                return JSON.toJSONString(response);


            }else {
                return "";
            }


        }catch (Exception e){
            return "";
        }
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CalculationBusinessLock calculationBusinessLock;

    @Override
    public String notify(String notifyResult) throws Exception {

        //解析
        Map<String, String> map = WXPayUtil.xmlToMap(notifyResult);

        //验签
        boolean flag = WXPayUtil.isSignatureValid(map, wxConfig.getPartnerKey());

        if (flag){

            if ("SUCCESS".equals(map.get("return_code"))){

                //获取多租户信息
                String tenantInfo = redisTemplate.opsForValue().get("tenantInfo");
                Map tenantMap = JSON.parseObject(tenantInfo, Map.class);
                TenantContext.addAttachments(tenantMap);

                String orderId = map.get("out_trade_no");
                OrderEntity orderEntity = orderService.getById(orderId);

                orderEntity.setPayStatus(SystemCode.ORDER_STATUS_PAYED);
                orderService.updateById(orderEntity);

                calculationBusinessLock.unLock(SystemCode.MICRO_APP_LOCK_PREFIX + orderEntity.getTableId());

                return orderEntity.getTableId();

            }else {
                log.error("支付回调失败:"+notifyResult);
            }

        }else {
            log.error("支付回调验签失败:"+notifyResult);
        }

        return null;
    }
}
