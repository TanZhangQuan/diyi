package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.constant.RoleConstant;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.tool.CollectionUtil;
import com.lgyun.common.tool.DateUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SecureUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.dto.GrantDTO;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author liangfeihu
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements IRoleService {

	IRoleMenuService roleMenuService;

	@Override
	public IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role) {
		return page.setRecords(baseMapper.selectRolePage(page, role));
	}

	@Override
	public List<RoleVO> tree(String tenantId) {
		String userRole = SecureUtil.getUserRole();
		String excludeRole = null;
		if (!CollectionUtil.contains(Func.toStrArray(userRole), RoleConstant.ADMIN)) {
			excludeRole = RoleConstant.ADMIN;
		}
		return ForestNodeMerger.merge(baseMapper.tree(tenantId, excludeRole));
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

	@Override
	public boolean grantFeign(GrantDTO request) {

        List<Long> roleIds = Arrays.asList(request.getAccountId());
        List<Long> menuIds = request.getMenuIds();

	    // 删除角色配置的菜单集合
		roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));

		// 组装配置
		List<RoleMenu> roleMenus = new ArrayList<>();
		roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setRoleId(roleId);
			roleMenu.setMenuId(menuId);

			roleMenu.setCreateUser(request.getUserId());
			roleMenu.setUpdateUser(request.getUserId());
			Date now = DateUtil.now();
			roleMenu.setCreateTime(now);
			roleMenu.setUpdateTime(now);
			if (roleMenu.getStatus() == null) {
				roleMenu.setStatus(BladeConstant.DB_STATUS_NORMAL);
			}
			roleMenu.setIsDeleted(BladeConstant.DB_NOT_DELETED);

			roleMenus.add(roleMenu);
		}));
		// 新增配置
		return roleMenuService.saveBatch(roleMenus);
	}

	@Override
	public String getRoleIds(String tenantId, String roleNames) {
		List<Role> roleList = baseMapper.selectList(Wrappers.<Role>query().lambda().eq(Role::getTenantId, tenantId).in(Role::getRoleName, Func.toStrList(roleNames)));
		if (roleList != null && roleList.size() > 0) {
			return roleList.stream().map(role -> Func.toStr(role.getId())).distinct().collect(Collectors.joining(","));
		}
		return null;
	}

	@Override
	public List<String> getRoleNames(String roleIds) {
		return baseMapper.getRoleNames(Func.toLongArray(roleIds));
	}

}
