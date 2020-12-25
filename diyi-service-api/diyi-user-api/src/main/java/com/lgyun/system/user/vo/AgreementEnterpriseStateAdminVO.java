package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AgreementEnterpriseStateAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "商户加盟")
    private Integer enterpriseJoinAgreement;

    @ApiModelProperty(value = "商户价格")
    private Integer enterprisePriceAgreement;

    @ApiModelProperty(value = "商户和创客的补充协议")
    private Integer entMakSupplementaryAgreement;

    @ApiModelProperty(value = "商户和服务商的补充协议")
    private Integer serEntSupplementaryAgreement;

    @ApiModelProperty(value = "商户业务真实性承诺函")
    private Integer enterprisePromise;

    @ApiModelProperty(value = "商户状态")
    private AccountState enterpriseState;
}
