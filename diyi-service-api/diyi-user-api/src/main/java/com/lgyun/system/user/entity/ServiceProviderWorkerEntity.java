package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 服务商员工表 Entity
 *
 * @author tzq
 * @since 2020-08-13 17:05:17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_service_provider_worker")
public class ServiceProviderWorkerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 服务商员工账户状态
     */
    private AccountState serviceProviderWorkerState = AccountState.NORMAL;

    /**
     * 姓名
     */
    private String workerName;

    /**
     * 性别
     */
    private String workerSex;

    /**
     * 岗位性质
     */
    private PositionName positionName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 上级主管
     */
    private Long upLevelId;

    /**
     * 用户名
     */
    private String employeeUserName;

    /**
     * 密码
     */
    private String employeePwd;

    /**
     * 管理员特性
     */
    private Boolean adminPower = false;

    /**
     * 是否拥有全部权限
     */
    private Boolean superAdmin = false;

}
