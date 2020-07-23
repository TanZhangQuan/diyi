package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelType;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("diyi_maker_enterprise")
public class MakerEnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 外包岗位ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long positionId;

    /**
     * 0：关联，1关注，2：不关联不关注
     */
    private Integer relationshipType;

    /**
     * 关联日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
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
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date cooperationStartTime;

    /**
     * 合作终止日期
     */
    @JsonFormat(pattern = DateUtil.PATTERN_DATETIME, timezone = "GMT+8")
    private Date cooperationEndTime;

}
