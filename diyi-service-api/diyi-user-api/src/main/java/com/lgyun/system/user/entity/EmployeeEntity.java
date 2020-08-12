package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.Gender;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_employee")
public class EmployeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    private String employeeName;

    /**
     * 性别：男，女
     */
    private Gender gender;

    /**
     * 岗位性质：营销人员，客服人员，运营人员，管理人员，其他
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
     * 用户名: 手机号码和用户名皆可
     */
    private String employeeUserName;

    /**
     * 密码
     */
    private String employeePwd;

    /**
     * 是否管理员
     */
    private Boolean adminPower;

}
