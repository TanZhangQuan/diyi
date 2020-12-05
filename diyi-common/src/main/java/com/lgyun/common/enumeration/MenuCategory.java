package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 */
@Getter
@AllArgsConstructor
public enum MenuCategory {
    MENU("MENU", "菜单"),
    BUTTON("BUTTON", "按钮");

    private final String value;
    private final String desc;

}