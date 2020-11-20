package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统部门 实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_dept")
public class Dept extends TenantEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 父主键
     */
    @ApiModelProperty(value = "父主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 部门名
     */
    @ApiModelProperty(value = "部门名")
    private String deptName;

    /**
     * 部门全称
     */
    @ApiModelProperty(value = "部门全称")
    private String fullName;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

}
