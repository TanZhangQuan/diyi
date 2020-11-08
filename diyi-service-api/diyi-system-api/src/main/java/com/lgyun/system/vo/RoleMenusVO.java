package com.lgyun.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class RoleMenusVO {

    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    private Long roleId;

    /**
     * 角色名
     */
    @ApiModelProperty("角色名")
    private String roleName;

    /**
     * 角色别名
     */
    @ApiModelProperty("角色别名")
    private String roleAlias;

    /**
     * 菜单ID字符集（ID之间用英文逗号隔开）
     */
    @ApiModelProperty("菜单ID集合")
    private List<String> menuNames;

}
