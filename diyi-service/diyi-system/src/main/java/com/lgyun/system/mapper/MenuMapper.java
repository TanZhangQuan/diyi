package com.lgyun.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.common.enumeration.MenuType;
import com.lgyun.common.node.TreeNode;
import com.lgyun.system.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author tzq
 */
public interface MenuMapper extends BaseMapper<Menu> {

    /**
     * 树形结构
     *
     * @param menuType
     * @return
     */
    List<TreeNode> tree(MenuType menuType);

    List<TreeNode> treeByRoleId(MenuType menuType, Long RoleId);

    /**
     * 所有菜单
     *
     * @param menuType
     * @return
     */
    @Select("select * from diyi_menu where menu_type=#{menuType} and bool_deleted = 0 and category = 1 ")
    List<Menu> allMenu(@Param("menuType") String menuType);

    /**
     * 权限配置菜单
     *
     * @param roleId
     * @return
     */
    List<Menu> roleMenu(List<Long> roleId);

    /**
     * 菜单树形结构
     *
     * @param roleId
     * @return
     */
    List<Menu> routes(List<Long> roleId);

    /**
     * 按钮树形结构
     *
     * @param roleId
     * @return
     */
    List<Menu> buttons(List<Long> roleId);

}
