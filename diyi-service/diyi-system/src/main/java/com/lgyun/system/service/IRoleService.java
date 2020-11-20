package com.lgyun.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.dto.GrantDTO;
import com.lgyun.system.entity.Role;
import com.lgyun.system.vo.RoleVO;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 服务类
 *
 * @author tzq
 */
public interface IRoleService extends BaseService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	IPage<RoleVO> selectRolePage(IPage<RoleVO> page, RoleVO role);

	/**
	 * 树形结构
	 *
	 * @param tenantId
	 * @return
	 */
	List<RoleVO> tree(String tenantId);

	/**
	 * 权限配置
	 *
	 * @param roleIds 角色id集合
	 * @param menuIds 菜单id集合
	 * @return 是否成功
	 */
	boolean grant(@NotEmpty List<Long> roleIds, @NotEmpty List<Long> menuIds);

	/**
	 * 权限配置
	 *
	 * @param request
	 * @return 是否成功
	 */
	boolean grantFeign(GrantDTO request);


	/**
	 * 查询角色ID
	 *
	 * @param tenantId
	 * @param roleNames
	 * @return
	 */
	String getRoleIds(String tenantId, String roleNames);

	/**
	 * 查询角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleNames(String roleIds);

}
