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
 * 渠道商员工表 Entity
 *
 * @author tzq
 * @since 2020-11-20 15:16:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agent_main_worker")
public class AgentMainWorkerEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商ID
     */
    private Long agentMainId;

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 商户员工账户状态
     */
    private AccountState agentMainWorkerState = AccountState.NORMAL;

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
     * 管理员权限
     */
    private Boolean superAdmin = false;

    /**
     * 管理员特性
     */
    private Boolean adminPower = false;

}
