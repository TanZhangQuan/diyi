package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.entity.RoleMenu;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author tzq
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    List<String> queryMenusByRole(Long roleId);
}
