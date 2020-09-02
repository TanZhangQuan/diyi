package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.entity.RoleMenu;
import com.lgyun.system.vo.RoleMenuVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author liangfeihu
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * 自定义分页
	 * @param page
	 * @param roleMenu
	 * @return
	 */
	List<RoleMenuVO> selectRoleMenuPage(IPage page, RoleMenuVO roleMenu);

	@Select("select menu_id from sys_role_menu where role_id=#{roleId} and is_deleted = 0")
	List<String> getUserMenus(@Param("roleId") Long roleId);

}
