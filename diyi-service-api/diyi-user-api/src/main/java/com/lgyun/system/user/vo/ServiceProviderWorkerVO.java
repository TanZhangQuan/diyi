package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class ServiceProviderWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

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
     * 菜单名称集合
     */
    private List<String> menuNames;

    /**
     * 账户状态描述
     */
    private AccountState accountState;

    /**
     * 是否是主账号
     */
    private Boolean master = false;

}
