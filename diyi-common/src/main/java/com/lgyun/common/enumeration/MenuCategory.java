package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 菜单类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MenuCategory {
    MENU("MENU", "菜单"),
    BUTTON("BUTTON", "按钮");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}