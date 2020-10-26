package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 实体类
 *
 * @author liangfeihu
 * @since 2020/6/6 19:21
 */
@Data
@TableName("sys_dict")
public class Dict extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父主键
     */
    @ApiModelProperty(value = "父主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 字典码
     */
    @ApiModelProperty(value = "字典码")
    private String code;

    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private Integer dictKey;

    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictValue;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 字典备注
     */
    @ApiModelProperty(value = "字典备注")
    private String remark;

    /**
     * 对应描述
     */
    @ApiModelProperty(value = "对应描述")
    private String describes;
}
