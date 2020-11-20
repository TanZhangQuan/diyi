package com.lgyun.system.feign;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.entity.Dept;
import com.lgyun.system.entity.Role;
import com.lgyun.system.dto.GrantDTO;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 系统服务模块 Feign失败配置
 *
 * @author tzq
 */
@Component
public class ISysClientFallback implements ISysClient {

    @Override
    public Dept getDept(Long id) {
        return null;
    }

    @Override
    public String getDeptName(Long id) {
        return null;
    }

    @Override
    public String getDeptIds(String tenantId, String deptNames) {
        return null;
    }

    @Override
    public List<String> getDeptNames(String deptIds) {
        return null;
    }

    @Override
    public String getPostIds(String tenantId, String postNames) {
        return null;
    }

    @Override
    public List<String> getPostNames(String postIds) {
        return null;
    }

    @Override
    public Role getRole(Long id) {
        return null;
    }

    @Override
    public String getRoleIds(String tenantId, String roleNames) {
        return null;
    }

    @Override
    public String getRoleName(Long id) {
        return null;
    }

    @Override
    public List<String> getRoleNames(String roleIds) {
        return null;
    }

    @Override
    public String getRoleAlias(Long id) {
        return null;
    }

    /**
     * 授权接口
     *
     * @param request
     * @return
     */
    @Override
    public R grantFeign(GrantDTO request) {
        return null;
    }

    @Override
    public R createOrUpdateRoleMenus(RoleMenusDTO roleMenusDTO, Long account) {
        return R.fail("操作失败！");
    }

    /**
     * 查询权限
     *
     * @param roleId 主键
     * @return
     */
    @Override
    public List<String> getMenuIds(Long roleId) {
        return null;
    }

    @Override
    public List<String> getMenuNames(Long roleId) {
        return null;
    }

    @Override
    public List<String> getMenuNamesAll(MenuType menuType) {
        return null;
    }

    @Override
    public List<RolesVO> getRoles(Long id, UserType userType) {
        return null;
    }

    @Override
    public R<List<RoleMenusVO>> getRoleMenusList(Long id) {
        return null;
    }

    @Override
    public R removeRole(Long roleId) {
        return R.fail("删除失败！");
    }

    @Override
    public R removeRoleMenu(String menus) {
        return R.fail("删除失败！");
    }
}
