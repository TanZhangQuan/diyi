package com.lgyun.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.enumeration.MenuCategory;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.enumeration.UserType;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.node.TreeNode;
import com.lgyun.common.tool.Func;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.entity.RoleMenu;
import com.lgyun.system.mapper.MenuMapper;
import com.lgyun.system.service.IMenuService;
import com.lgyun.system.service.IRoleMenuService;
import com.lgyun.system.vo.MenuVO;
import com.lgyun.system.wrapper.MenuWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author tzq
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends BaseServiceImpl<MenuMapper, Menu> implements IMenuService {

    IRoleMenuService roleMenuService;

    @Override
    public List<MenuVO> routes(String roleId, UserType userType, Boolean superAdmin) {
        List<Menu> allMenus = baseMapper.allMenu(userType.getValue());
        List<Menu> roleMenus;
        if (superAdmin) {
            roleMenus = allMenus;
        } else {
            roleMenus = baseMapper.roleMenu(Func.toLongList(roleId));
        }
        List<Menu> routes = new LinkedList<>(roleMenus);
        roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
        routes.sort(Comparator.comparing(Menu::getSort));
        MenuWrapper menuWrapper = new MenuWrapper();
        List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), MenuCategory.MENU)).collect(Collectors.toList());
        return menuWrapper.listNodeVO(collect);
    }

    public void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
        Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
        if (menu.isPresent() && !routes.contains(menu.get())) {
            routes.add(menu.get());
            recursion(allMenus, routes, menu.get());
        }
    }

    @Override
    public List<TreeNode> tree(MenuType menuType, Long roleId, Boolean superAdmin) {
        if (superAdmin) {
            return ForestNodeMerger.merge(baseMapper.tree(menuType));
        }
        return ForestNodeMerger.merge(baseMapper.treeByRoleId(menuType, roleId));
    }

    @Override
    public List<String> roleTreeKeys(String roleIds) {
        List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
        return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
    }

}
