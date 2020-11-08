package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
     * 管理员账户状态
     */
    @ApiModelProperty("管理员账户状态")
    private String adminState;

    /**
     * 是否为主账号（为true时为主账号）
     */
    private Boolean master;

    /**
     * 拥有的菜单名称
     */
    @ApiModelProperty("拥有的菜单名称集合")
    private List<String> menuNames;

}
