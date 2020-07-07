package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_worksheet_attention")
public class WorksheetAttentionEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetAttentionId;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 企业ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 工单关注编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetAttentionNo;

}
