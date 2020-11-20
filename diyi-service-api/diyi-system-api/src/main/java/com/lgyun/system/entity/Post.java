package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.TenantEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 岗位表 实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_post")
public class Post extends TenantEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 类型
     */
    @ApiModelProperty(value = "类型")
    private Integer category;

    /**
     * 岗位编号
     */
    @ApiModelProperty(value = "岗位编号")
    private String postCode;

    /**
     * 岗位名称
     */
    @ApiModelProperty(value = "岗位名称")
    private String postName;

    /**
     * 岗位排序
     */
    @ApiModelProperty(value = "岗位排序")
    private Integer sort;

    /**
     * 岗位描述
     */
    @ApiModelProperty(value = "岗位描述")
    private String remark;

}
