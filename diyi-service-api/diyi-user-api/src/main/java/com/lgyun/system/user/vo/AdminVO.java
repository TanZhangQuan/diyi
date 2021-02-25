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
public class AdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "账号ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "岗位性质")
    private PositionName positionName;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "管理员账户状态")
    private AccountState adminState;

    @ApiModelProperty(value = "是否为主账号(为true时为主账号)")
    private Boolean master = false;

    @ApiModelProperty(value = "拥有的菜单名称")
    private List<String> menuNames;

}
