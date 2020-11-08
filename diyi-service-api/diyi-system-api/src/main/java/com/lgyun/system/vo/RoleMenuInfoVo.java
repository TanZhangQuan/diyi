package com.lgyun.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleMenuInfoVo {
    /**
     * 角色ID
     */
    @ApiModelProperty("角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
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
    private List<String> menuIds;
}