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
public class AdminVO implements Serializable {

    /**
     * 账号ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    @ApiModelProperty("姓名")
    private String name;

    /**
     * 岗位性质
     */
    @ApiModelProperty("岗位性质")
    private PositionName positionName;

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
     * 管理员账户状态
     */
    @ApiModelProperty("管理员账户状态")
    private AccountState adminState;

    /**
     * 是否为主账号（为true时为主账号）
     */
    private Boolean master = false;

    /**
     * 拥有的菜单名称
     */
    @ApiModelProperty("拥有的菜单名称集合")
    private List<String> menuNames;

}
