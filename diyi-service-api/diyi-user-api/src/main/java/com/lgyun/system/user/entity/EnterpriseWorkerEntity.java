package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.EnterprisePositionName;
import com.lgyun.common.enumeration.Gender;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
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
     * 管理者ID
     */
    @JsonIgnore
    private Long userId;

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
    private Gender workerSex;

    /**
     * 岗位性质
     */
    private EnterprisePositionName positionName;

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
    @JsonIgnore
    private String employeePwd;

    /**
     * 管理员特性
     */
    private Boolean adminPower;

    /**
     * 超级管理员
     */
    private Boolean superAdmin;

}
