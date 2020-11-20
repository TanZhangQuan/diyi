package com.lgyun.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.system.entity.Menu;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import com.lgyun.common.node.INode;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单 视图实体类
 *
 * @author tzq
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MenuVO extends Menu implements INode {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 父节点ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long parentId;

    /**
     * 子孙节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<INode> children;

    @Override
    public List<INode> getChildren() {
        if (this.children == null) {
            this.children = new ArrayList<>();
        }
        return this.children;
    }

    /**
     * 上级菜单
     */
    private String parentName;

    /**
     * 菜单类型
     */
    private String categoryName;

    /**
     * 按钮功能
     */
    private String actionName;

    /**
     * 是否新窗口打开
     */
    private String isOpenName;
}
