package com.lgyun.system.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel("角色信息（以下拉框形式显示,为空时表示没有创建角色,提醒用户去创建角色）")
public class RolesVO implements Serializable {
    @ApiModelProperty("角色ID")
    private Long id;
    @ApiModelProperty("角色名称")
    private String roleName;
}
