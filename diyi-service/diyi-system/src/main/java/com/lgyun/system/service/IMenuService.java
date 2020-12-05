package com.lgyun.system.service;

import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.node.TreeNode;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.vo.MenuVO;

import java.util.List;

/**
 * 服务类
 *
 * @author tzq
 */
public interface IMenuService extends BaseService<Menu> {

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @param superAdmin
	 * @return
	 */
	List<MenuVO> routes(String roleId, UserType userType, Boolean superAdmin);

	/**
	 * 树形结构
	 *
     * @param menuType
	 * @param roleId
	 * @param superAdmin
	 * @return
	 */
	List<TreeNode> tree(MenuType menuType, Long roleId, Boolean superAdmin);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> roleTreeKeys(String roleIds);

}
