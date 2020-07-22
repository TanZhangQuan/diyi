package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 1，开启中；2，已关闭
 * @author .
 * @date 2020/7/22.
 * @time 14:18.
 */
@Getter
@AllArgsConstructor
public enum TemplateSignState {
    OPEN("OPEN", "开启"),
    CLOSE("CLOSE", "关闭");

    private final String value;
    private final String desc;
}
