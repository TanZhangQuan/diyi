package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 渠道商人员表 Entity
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agent_person")
public class AgentPersonEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 渠道商ID
     */
    private Long agentMainId;

    /**
     * 工作人员ID
     */
    private Long workerId;

    /**
     * 头像
     */
    private String avatar;

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
    private PositionName positionName = PositionName.MARKETING;

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
}
