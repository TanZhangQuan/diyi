package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PositionName;
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
     * 角色ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位性质
     */
    private PositionName positionName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户名
     */
    private String userName;

    /**
     *  创建子账号的权限
     */
    private Boolean adminPower;

}
