package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.entity.RoleMenu;
import com.lgyun.system.mapper.RoleMenuMapper;
import com.lgyun.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务实现类
 *
 * @author liangfeihu
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public List<String> getUserMenus(Long roleId) {
        return this.baseMapper.getUserMenus(roleId);
    }

}
