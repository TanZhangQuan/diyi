package com.lgyun.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.system.entity.RoleMenu;

import java.util.List;

/**
 * 服务类
 *
 * @author liangfeihu
 */
public interface IRoleMenuService extends IService<RoleMenu> {

    List<String> getUserMenus(Long roleId);
}
