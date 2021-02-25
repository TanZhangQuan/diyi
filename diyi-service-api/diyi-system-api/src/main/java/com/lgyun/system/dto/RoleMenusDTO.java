package com.lgyun.system.dto;

import com.lgyun.common.enumeration.UserType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class RoleMenusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "角色名")
    @NotBlank(message = "请输入角色名")
    private String roleName;

    @ApiModelProperty(value = "菜单集合(多个逗号隔开)")
    @NotBlank(message = "请选择菜单")
    private String menus;

    @ApiModelProperty(value = "用户类型", notes = "com.lgyun.common.enumeration.UserType")
    private UserType userType;
}
