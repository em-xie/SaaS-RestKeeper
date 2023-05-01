package com.restkeeper.operator.dto;

import lombok.Data;

/**
 * @作者：xie
 * @时间：2023/5/1 16:07
 */
@Data
public class AlarmDTO {

    private Integer scopeId;
    private String scope;
    private String name;
    private Integer id0;
    private Integer id1;
    private String ruleName;
    private String alarmMessage;
    private Long startTime;
}
