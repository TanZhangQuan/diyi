package com.lgyun.system.dto;

import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(description = "添加角色及分配权限")
public class RoleMenusDTO {

    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 角色名
     */
    @NotBlank(message = "请输入角色名")
    private String roleName;

    /**
     * 角色别名
     */
    @NotBlank(message = "请输入角色别名")
    private String roleAlias;

    /**
     * 菜单ID字符集（ID之间用英文逗号隔开）
     */
    @NotBlank(message = "请选择菜单")
    private String menus;

    /**
     * 用户类型
     */
    private UserType userType;
}
