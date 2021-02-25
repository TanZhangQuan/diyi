package com.lgyun.system.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class RoleMenusVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long roleId;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "菜单集合(多个逗号隔开)")
    private List<String> menuNames;

}
