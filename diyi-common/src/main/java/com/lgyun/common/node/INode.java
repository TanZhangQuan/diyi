package com.lgyun.common.node;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Blade.
 *
 * @author liangfeihu
 */
public interface INode extends Serializable {

	/**
	 * 主键
	 *
	 * @return Integer
	 */
	Long getId();

	/**
	 * 父主键
	 *
	 * @return Integer
	 */
	Long getParentId();

	/**
	 * 子孙节点
	 *
	 * @return List
	 */
	List<INode> getChildren();

}
