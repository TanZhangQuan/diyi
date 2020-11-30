package com.lgyun.system.feign;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.system.dto.RoleMenusDTO;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.entity.Role;
import com.lgyun.system.entity.RoleMenu;
import com.lgyun.system.service.IMenuService;
import com.lgyun.system.service.IRoleMenuService;
import com.lgyun.system.service.IRoleService;
import com.lgyun.system.vo.RoleMenusVO;
import com.lgyun.system.vo.RolesVO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统服务Feign实现类
 *
 * @author tzq
 */
@ApiIgnore
@RestController
@AllArgsConstructor
public class SysClient implements ISysClient {

    private IRoleService roleService;
    private IMenuService iMenuService;
    private IRoleMenuService roleMenuService;

    @Override
    @GetMapping(API_PREFIX + "/getRole")
    public Role getRole(Long id) {
        return roleService.getById(id);
    }

    @Override
    @PostMapping(API_PREFIX + "/create-or-update-role-menus")
    @Transactional(rollbackFor = Exception.class)
    public R createOrUpdateRoleMenus(@RequestBody RoleMenusDTO roleMenusDTO, Long account) {
        Role role;
        if (roleMenusDTO.getRoleId() != null && roleMenusDTO.getRoleId() != 0) {
            role = roleService.getById(roleMenusDTO.getRoleId());
            roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, role.getId()));
        } else {
            role = new Role();
        }
        BeanUtil.copyProperties(roleMenusDTO, role);
        role.setAccount(account);
        role.setUserType(roleMenusDTO.getUserType());
        roleService.saveOrUpdate(role);
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : Func.toLongList(roleMenusDTO.getMenus())) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setMenuId(menuId);
            roleMenu.setRoleId(role.getId());
            roleMenus.add(roleMenu);
        }
        return R.status(roleMenuService.saveBatch(roleMenus));
    }

    @Override
    public List<String> queryMenusByRole(Long roleId) {
        return roleMenuService.queryMenusByRole(roleId);
    }

    @Override
    @GetMapping(API_PREFIX + "/menu-names")
    public List<String> getMenuNames(Long roleId) {
        List<String> menuNames = new ArrayList<>();
        List<String> menuIds = this.queryMenusByRole(roleId);
        if (menuIds == null || menuIds.size() <= 0) {
            return menuNames;
        }
        List<Menu> menus = iMenuService.listByIds(menuIds);
        menus.forEach(menu -> {
            menuNames.add(menu.getName());
        });
        return menuNames;
    }

    @Override
    @PostMapping(API_PREFIX + "/menu-names-all")
    public List<String> getMenuNamesAll(@RequestBody MenuType menuType) {
        List<Menu> list = iMenuService.list(new QueryWrapper<Menu>().lambda().eq(Menu::getMenuType, menuType));
        List<String> menuNames = new ArrayList<>();
        list.forEach(menu -> {
            menuNames.add(menu.getName());
        });
        return menuNames;
    }

    @Override
    @PostMapping(API_PREFIX + "/roles")
    public List<RolesVO> getRoles(@NotBlank(message = "请选择用户") @RequestParam("id") Long id, @RequestBody UserType userType) {
        List<Role> list = roleService.list(new QueryWrapper<Role>().lambda().eq(Role::getUserType, userType).eq(Role::getAccount, id));
        List<RolesVO> rolesVOS = new ArrayList<>();
        list.forEach(role -> {
            RolesVO rolesVO = new RolesVO();
            BeanUtil.copyProperties(role, rolesVO);
            rolesVOS.add(rolesVO);
        });
        return rolesVOS;
    }

    @Override
    @GetMapping(API_PREFIX + "/get-role-menus-list")
    public R<List<RoleMenusVO>> getRoleMenusList(Long id) {
        List<Role> list = roleService.list(new QueryWrapper<Role>().lambda().eq(Role::getAccount, id));
        List<RoleMenusVO> roleMenusVOS = new ArrayList<>();
        list.forEach(role -> {
            RoleMenusVO roleMenusVO = new RoleMenusVO();
            BeanUtils.copyProperties(role, roleMenusVO);
            roleMenusVO.setRoleId(role.getId());
            List<String> menuNames = this.getMenuNames(role.getId());
            roleMenusVO.setMenuNames(menuNames);
            roleMenusVOS.add(roleMenusVO);
        });
        return R.data(roleMenusVOS);
    }

    @Override
    @GetMapping(API_PREFIX + "/remove-role")
    @Transactional(rollbackFor = Exception.class)
    public R<String> removeRole(Long roleId) {
        roleService.removeById(roleId);
        roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId));
        return R.fail("删除成功！");
    }

    @Override
    @GetMapping(API_PREFIX + "/remove-role-menu")
    public R removeRoleMenu(@RequestBody String menus) {
        roleMenuService.remove(new QueryWrapper<RoleMenu>().lambda().in(RoleMenu::getMenuId, Func.toLongArray(menus)));
        return R.fail("删除成功！");
    }
}
