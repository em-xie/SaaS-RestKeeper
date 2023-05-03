package com.restkeeper.order.weixin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = "classpath:wxpay.properties")
public class WXConfig{
    @Value("${wxpay.appId}")
    private String appId;
    @Value("${wxpay.appSecret}")
    private String appSecret;
    @Value("${wxpay.mchId}")
    private String mchId;
    @Value("${wxpay.partnerKey}")
    private String partnerKey;

    @Value("${wxpay.domain}")
    private String notifyUrl = "";

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public String getMchId() {
        return mchId;
    }

    public String getPartnerKey() {
        return partnerKey;
    }

    public String getNotifyUrl() {return notifyUrl;}
}
