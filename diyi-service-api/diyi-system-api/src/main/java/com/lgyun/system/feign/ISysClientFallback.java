package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.exception.CustomException;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.entity.Role;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 系统服务模块 Feign失败配置
 *
 * @author tzq
 */
@Component
public class ISysClientFallback implements ISysClient {

    @Override
    public Role getRole(Long id) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> createOrUpdateRoleMenus(RoleMenusDTO roleMenusDTO, Long account) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public List<String> queryMenusByRole(Long roleId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public List<String> getMenuNames(Long roleId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public List<String> getMenuNamesAll(MenuType menuType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public List<RolesVO> getRoles(Long id, UserType userType) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<List<RoleMenusVO>> getRoleMenusList(Long id) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> removeRole(Long roleId) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }

    @Override
    public R<String> removeRoleMenu(String menus) {
        throw new CustomException("网络繁忙，请稍后尝试");
    }
}
