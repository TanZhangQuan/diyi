package com.lgyun.system.dto;

import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "添加角色及分配权限")
public class RoleMenusDTO {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID(编辑时不能为空！)")
    private Long roleId;

    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    @NotBlank(message = "角色名不能为空！")
    private String roleName;

    /**
     * 角色别名
     */
    @ApiModelProperty("角色别名")
    @NotBlank(message = "角色别名不能为空！")
    private String roleAlias;

    /**
     * 菜单ID字符集（ID之间用英文逗号隔开）
     */
    @ApiModelProperty("菜单ID字符集（ID之间用英文逗号隔开）")
    @NotBlank(message = "菜单ID字符集不能为空！")
    private String menus;

    @ApiModelProperty(value = "用户类型",hidden = true)
    private UserType userType;
}
