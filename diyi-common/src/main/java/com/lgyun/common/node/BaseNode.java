package com.lgyun.common.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类
 *
 * @author tzq
 */
@Data
public class BaseNode implements INode {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	protected Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	protected Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected List<INode> children = new ArrayList<>();

}
