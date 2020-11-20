package com.lgyun.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 系统菜单 实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
public class Menu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 菜单父主键
     */
    @ApiModelProperty(value = "父主键")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String code;

    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;

    /**
     * 菜单别名
     */
    @ApiModelProperty(value = "菜单别名")
    private String alias;

    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String path;

    /**
     * 菜单资源
     */
    @ApiModelProperty(value = "菜单资源")
    private String source;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型")
    private Integer category;

    /**
     * 操作按钮类型
     */
    @ApiModelProperty(value = "操作按钮类型")
    private Integer action;

    /**
     * 是否打开新页面
     */
    @ApiModelProperty(value = "是否打开新页面")
    private Integer isOpen;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 菜单类型：商户、服务商、管理平台
     */
    @ApiModelProperty(value = "菜单类型：商户、服务商、管理平台")
    private String menuType;

}
