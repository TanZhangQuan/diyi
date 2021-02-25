package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXXX")
public class EnterpriseWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseWorkerId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "岗位")
    private PositionName positionName;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "用户名")
    private String employeeUserName;

    @ApiModelProperty(value = "拥有的菜单名称集合")
    private List<String> menuName;

    @ApiModelProperty(value = "账户状态")
    private AccountState accountState;

    @ApiModelProperty(value = "是否为主账号（为true时为主账号）")
    private Boolean master;

}
