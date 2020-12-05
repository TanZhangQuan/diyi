package com.lgyun.system.wrapper;

import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.service.IMenuService;
import com.lgyun.system.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author tzq
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

    private static IMenuService menuService;

    static {
        menuService = SpringUtil.getBean(IMenuService.class);
    }

    @Override
    public MenuVO entityVO(Menu menu) {
        MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
        if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
            menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
        } else {
            Menu parent = menuService.getById(menu.getParentId());
            menuVO.setParentName(parent.getName());
        }

        return menuVO;
    }


    public List<MenuVO> listNodeVO(List<Menu> list) {
        List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }

}
