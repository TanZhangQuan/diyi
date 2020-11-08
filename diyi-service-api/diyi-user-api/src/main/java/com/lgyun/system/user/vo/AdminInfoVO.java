package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AdminInfoVO implements Serializable {

    /**
     * 账号ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String id;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 岗位性质
     */
    @ApiModelProperty("岗位性质")
    private String positionName;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phoneNumber;

    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String userName;


    /**
     * 拥有的角色ID
     */
    @ApiModelProperty("角色ID(当查询自己时角色显示为空切不可编辑)")
    private Long roleId;

}
