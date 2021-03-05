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
public class AgreementServiceStateAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商id")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "服务商加盟")
    private Integer serviceProviderJoinAgreement;

    @ApiModelProperty(value = "服务商和商户的补充协议")
    private Integer serEntSupplementaryAgreement;

    @ApiModelProperty(value = "服务商的状态")
    private AccountState serviceProviderState;

    /**
     *
     *服务商和创客的补充协议
     */
    private Integer serMakSupplementaryAgreement;
}
