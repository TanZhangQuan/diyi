package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class ServiceProviderWorkerInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 角色ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 岗位
     */
    private PositionName positionName;


    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 用户名
     */
    private String employeeUserName;

    /**
     * 建立子账号权限
     */
    private Boolean adminPower;


}
