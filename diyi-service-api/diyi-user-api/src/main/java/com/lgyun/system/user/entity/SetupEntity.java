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
@TableName("diyi_setup")
public class SetupEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 特殊配置ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long setupId;

    /**
     * 允许第一次关联企业更新创客信息
     */
    private Boolean allowFirstEdit;

    /**
     * 允许所有关联企业更新创客信息
     */
    private Boolean allowAllEdit;

}
