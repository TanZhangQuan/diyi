package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模板状态 1,应用中；2，已过期。同一个模板上传新模板后，原来的模板即为已过期
 * @author jun.
 * @date 2020/7/22.
 * @time 9:32.
 */
@Getter
@AllArgsConstructor
public enum TemplateState {
    APPLICATION("APPLICATION", "应用中"),
    OVERDUE("OVERDUE", "已过期");

    private final String value;
    private final String desc;
}
