package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import io.swagger.annotations.ApiModelProperty;
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
public class EnterpriseWorkerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    @ApiModelProperty("商户员工ID（编辑时用来用来查询账户详情）")
    private Long enterpriseWorkerId;

    /**
     * 商户名称
     */
    @ApiModelProperty("商户名称")
    private String enterpriseName;

    /**
     * 岗位
     */
    @ApiModelProperty("岗位")
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
    private String employeeUserName;

    /**
     * 拥有的菜单名称集合
     */
    @ApiModelProperty("拥有的菜单名称集合(权限)")
    private List<String> menuName;

    /**
     * 账户状态
     */
    @ApiModelProperty("账户状态")
    private AccountState accountState;

    /**
     * 是否为主账号（为true时为主账号）
     */
    @ApiModelProperty("是否为主账号（为true时为主账号）")
    private Boolean master = false;

}
