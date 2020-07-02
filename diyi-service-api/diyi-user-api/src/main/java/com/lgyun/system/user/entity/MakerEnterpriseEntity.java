package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelType;
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
@TableName("diyi_maker_enterprise")
public class MakerEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 创客和外包企业的关联关系ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    /**
     * 企业ID
     */
    private Long enterpriseId;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 外包岗位ID
     */
    private Long positionId;

    private Integer relationshipType;

    /**
     * 关联日期
     */
    private Date relDate;

    /**
     * 关联类型：创客主动关联，企业主动关联，平台关联
     */
    private RelType relType;

    /**
     * 关联备注
     */
    private String relMemo;

    /**
     * 合作状态：合作中，停止合作；首次关联时默认为合作中
     */
    private CooperateStatus cooperateStatus;

    /**
     * 创客第一次合作
     */
    private Boolean firstCooperation;

    /**
     * 合作开始日期
     */
    private Date cooperationStartTime;

    /**
     * 合作终止日期
     */
    private Date cooperationEndTime;

}
