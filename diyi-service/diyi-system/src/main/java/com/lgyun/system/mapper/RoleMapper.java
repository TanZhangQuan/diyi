package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.entity.Role;
import com.lgyun.system.vo.RoleVO;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author tzq
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 查询树形节点
	 *
	 * @param tenantId
	 * @param excludeRole
	 * @return
	 */
	List<RoleVO> tree(String tenantId, String excludeRole);

	/**
	 * 查询角色名
	 *
	 * @param ids
	 * @return
	 */
	List<String> getRoleNames(Long[] ids);

}
