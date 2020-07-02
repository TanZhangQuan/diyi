package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 *  Entity
 *
 * @author jun
 * @since 2020-06-29 14:46:14
 */
@Data
@NoArgsConstructor
@TableName("diyi_work_achievement")
public class WorkAchievementEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 工作成果Id
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
        private Long workAchievementId;

    @ApiModelProperty(value = "工单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    /**
     * 工作成果说明
     */
        private String workExplain;

    /**
     * 工作结果url
     */
        private String workUrl;

    /**
     * 验收金额
     */
        private BigDecimal checkMoneyNum;

    /**
     * 工作成果状态 1：待验收，2验收通过，3验收不通过
     */
        private String workAchievementState;

    /**
     * 创建人
     */
        private Long createUser;

    /**
     * 创建时间
     */
        private Date createTime;

    /**
     * 更新人
     */
        private Long updateUser;

    /**
     * 更新时间
     */
        private Date updateTime;

    /**
     * 状态[1:正常]
     */
        private Integer status;

    /**
     * 状态[0:未删除,1:删除]
     */
        private Integer isDeleted;
    }
