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
 * 商户账号 Entity
 *
 * @author tzq
 * @since 2020-07-23 17:50:16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_enterprise_worker")
public class EnterpriseWorkerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 拥有的角色ID
     */
    private Long roleId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 商户员工账户状态
     */
    private AccountState enterpriseWorkerState = AccountState.NORMAL;

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
    private Boolean superAdmin = false;

    /**
     * 管理员特性
     */
    private Boolean adminPower = false;

}
