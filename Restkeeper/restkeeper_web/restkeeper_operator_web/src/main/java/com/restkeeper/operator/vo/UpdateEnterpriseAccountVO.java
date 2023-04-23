package com.restkeeper.operator.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @作者：xie
 * @时间：2023/4/23 10:22
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UpdateEnterpriseAccountVO extends AddEnterpriseAccountVO {

    @ApiModelProperty("企业id")
    private String enterpriseId;
}
