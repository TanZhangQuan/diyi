package com.lgyun.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.node.INode;
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
public class DictVO implements INode {
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
	 * 上级字典
	 */
	private String dictValue;

	/**
	 * 排序
	 */
	private Integer sort;

	/**
	 * 字典备注
	 */
	private String remark;

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
}
