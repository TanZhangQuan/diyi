package com.lgyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.node.TreeNode;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.common.support.Kv;
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
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @param superAdmin
	 * @return
	 */
	List<MenuVO> routes(String roleId, UserType userType, Boolean superAdmin);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> buttons(String roleId);

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
	 * 授权树形结构
	 *
	 * @param user
	 * @return
	 */
	List<MenuVO> grantTree(BladeUser user);

	/**
	 * 默认选中节点
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> roleTreeKeys(String roleIds);

	/**
	 * 查询配置的角色权限
	 *
	 * @param user
	 * @return
	 */
	List<Kv> authRoutes(BladeUser user);

}
