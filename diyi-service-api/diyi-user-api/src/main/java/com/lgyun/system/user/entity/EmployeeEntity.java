package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.Gender;
import com.lgyun.common.enumeration.PositionName;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Data
@NoArgsConstructor
@TableName("diyi_employee")
public class EmployeeEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 平台工作人员的基本信息（销售、运营和管理人员等）ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long employeeId;

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
