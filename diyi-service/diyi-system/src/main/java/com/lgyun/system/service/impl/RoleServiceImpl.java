package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.entity.Role;
import com.lgyun.system.entity.RoleMenu;
import com.lgyun.system.mapper.RoleMapper;
import com.lgyun.system.service.IRoleMenuService;
import com.lgyun.system.service.IRoleService;
import com.lgyun.system.vo.RoleVO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实现类
 *
 * @author tzq
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

	IRoleMenuService roleMenuService;

	@Override
	public List<RoleVO> tree(String tenantId) {
		return ForestNodeMerger.merge(baseMapper.tree(tenantId));
	}

	@Override
	public boolean grant(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds) {
		// 删除角色配置的菜单集合
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
		// 组装配置
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);

			roleMenus.add(roleMenu);
		}));
		// 新增配置
		return roleMenuService.saveBatch(roleMenus);
	}

}
