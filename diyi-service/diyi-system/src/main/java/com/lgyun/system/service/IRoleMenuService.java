package com.lgyun.system.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.entity.RoleMenu;

import java.util.List;

/**
 * 服务类
 *
 * @author tzq
 */
public interface IRoleMenuService extends BaseService<RoleMenu> {

    List<String> queryMenusByRole(Long roleId);
}
