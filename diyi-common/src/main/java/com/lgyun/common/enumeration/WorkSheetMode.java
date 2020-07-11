package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模式，派单、抢单、混合（默认：混合型）
 * @author jun.
 * @date 2020/7/7.
 * @time 15:11.
 */
@Getter
@AllArgsConstructor
public enum WorkSheetMode {
    DISPATCH("DISPATCH", "派单"),
    GRABBING("GRABBING", "抢单"),
    BLEND("BLEND", "混合");

    private final String value;
    private final String desc;

}
