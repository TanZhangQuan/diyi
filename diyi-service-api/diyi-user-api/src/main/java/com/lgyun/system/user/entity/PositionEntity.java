package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
 * @since 2020-06-26 17:21:06
 */
@Data
@NoArgsConstructor
@TableName("diyi_position")
public class PositionEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 外包岗位的基本信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long positionId;

    /**
     * 外包岗位名称
     */
    private PositionName positionName;

    /**
     * 外包岗位描述
     */
    private String positionDesc;

}
