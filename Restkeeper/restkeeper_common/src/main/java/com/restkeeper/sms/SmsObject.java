package com.restkeeper.sms;

import lombok.Data;

/**
 * @作者：xie
 * @时间：2023/4/23 10:55
 */
@Data
public class SmsObject {
    //网络传输对象必须序列化
    private static final long serialVersionUID = -6986749569115643762L;

    private String phoneNumber;

    private String signName;

    private String templateCode;

    private String templateJsonParam;
}
