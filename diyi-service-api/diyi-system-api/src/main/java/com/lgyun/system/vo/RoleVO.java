package com.lgyun.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.node.INode;
import com.lgyun.system.entity.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "角色VO")
@EqualsAndHashCode(callSuper = true)
public class RoleVO extends Role implements INode {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "父节点ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    @ApiModelProperty(value = "子孙节点")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<INode> children;

    @Override
    public List<INode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    @ApiModelProperty(value = "上级角色")
    private String parentName;
}
