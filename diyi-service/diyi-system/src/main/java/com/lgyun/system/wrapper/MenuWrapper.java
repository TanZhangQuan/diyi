package com.lgyun.system.wrapper;

import com.lgyun.common.api.R;
import com.lgyun.common.constant.CommonConstant;
import com.lgyun.common.node.ForestNodeMerger;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SpringUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.entity.Menu;
import com.lgyun.system.service.IMenuService;
import com.lgyun.system.feign.IDictClient;
import com.lgyun.system.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

    private static IMenuService menuService;

    private static IDictClient dictClient;

    static {
        menuService = SpringUtil.getBean(IMenuService.class);
        dictClient = SpringUtil.getBean(IDictClient.class);
    }

    public static MenuWrapper build() {
        return new MenuWrapper();
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
        R<String> d1 = dictClient.getValue("menu_category", String.valueOf(menuVO.getCategory()));
        R<String> d2 = dictClient.getValue("button_func", String.valueOf(menuVO.getAction()));
        R<String> d3 = dictClient.getValue("yes_no", String.valueOf(menuVO.getIsOpen()));
        if (d1.isSuccess()) {
            menuVO.setCategoryName(d1.getData());
        }
        if (d2.isSuccess()) {
            menuVO.setActionName(d2.getData());
        }
        if (d3.isSuccess()) {
            menuVO.setIsOpenName(d3.getData());
        }
        return menuVO;
    }


    public List<MenuVO> listNodeVO(List<Menu> list) {
        List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }

}
