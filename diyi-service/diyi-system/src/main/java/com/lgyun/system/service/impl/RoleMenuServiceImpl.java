package com.lgyun.system.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.entity.RoleMenu;
import com.lgyun.system.mapper.RoleMenuMapper;
import com.lgyun.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author tzq
 */
@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<String> getUserMenus(Long roleId) {
        return this.baseMapper.getUserMenus(roleId);
    }
}
