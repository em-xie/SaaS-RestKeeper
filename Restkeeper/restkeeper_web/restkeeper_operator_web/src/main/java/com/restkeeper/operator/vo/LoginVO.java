package com.restkeeper.operator.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @作者：xie
 * @时间：2023/4/23 8:44
 */
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "登录账号")
    private String loginName;

    @ApiModelProperty(value = "密码")
    private String loginPass;
}