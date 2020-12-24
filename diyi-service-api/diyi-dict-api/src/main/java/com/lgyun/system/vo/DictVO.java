package com.lgyun.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.node.INode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 19:18
 */
@Data
@ApiModel(description = "字典VO")
public class DictVO implements INode {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "字典ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long id;

	@ApiModelProperty(value = "上级字典ID")
	@JsonSerialize(using = ToStringSerializer.class)
	private Long parentId;

	@ApiModelProperty(value = "字典名称")
	private String dictValue;

	@ApiModelProperty(value = "字典备注")
	private String remark;

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
}
