package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class ServiceProviderAccountListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商收款账户信息ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "账户类型")
    private AccountType accountType;

    @ApiModelProperty(value = "账户名称")
    private String accountName;

    @ApiModelProperty(value = "银行账号")
    private String accountNo;

    @ApiModelProperty(value = "开户银行")
    private String accountBank;

    @ApiModelProperty(value = "是否默认")
    private Boolean boolDefault;

}
