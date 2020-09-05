package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.node.TreeNode;
import com.lgyun.system.dto.MenuDTO;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.vo.MenuVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author liangfeihu
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param menu
	 * @return
	 */
	List<MenuVO> selectMenuPage(IPage page, MenuVO menu);

	/**
	 * 树形结构
	 *
	 * @param menuType
	 * @return
	 */
	List<TreeNode> tree(String menuType);

	/**
	 * 授权树形结构
	 *
	 * @return
	 */
	List<MenuVO> grantTree();

	/**
	 * 授权树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<MenuVO> grantTreeByRole(List<Long> roleId);

	/**
	 * 所有菜单
	 *
	 * @param menuType
	 * @return
	 */
	@Select("select * from sys_menu where menu_type=#{menuType} and is_deleted = 0 and category = 1 ")
	List<Menu> allMenu(@Param("menuType") String menuType);

	/**
	 * 权限配置菜单
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> roleMenu(List<Long> roleId);

	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> routes(List<Long> roleId);

	/**
	 * 按钮树形结构
	 *
	 * @param roleId
	 * @return
	 */
	List<Menu> buttons(List<Long> roleId);

	/**
	 * 获取配置的角色权限
	 *
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> authRoutes(List<Long> roleIds);
}
