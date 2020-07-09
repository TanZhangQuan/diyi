package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 类型，总包+分包，众包/众采
 * @author jun.
 * @date 2020/7/7.
 * @time 15:11.
 */
@Getter
public enum WorkSheetType {
    SUBPACKAGE("SUBPACKAGE", "总包+分包"),
    CROWDSOURCED("CROWDSOURCED", "众包/众采");

    private String value;
    private String desc;

    WorkSheetType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
