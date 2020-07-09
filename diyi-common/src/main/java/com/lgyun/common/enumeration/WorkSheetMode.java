package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 模式，派单、抢单、混合（默认：混合型）
 * @author jun.
 * @date 2020/7/7.
 * @time 15:11.
 */
@Getter
public enum WorkSheetMode {
    DISPATCH("DISPATCH", "派单"),
    GRABBING("GRABBING", "抢单"),
    BLEND("BLEND", "混合");


    private String value;
    private String desc;

    WorkSheetMode(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
