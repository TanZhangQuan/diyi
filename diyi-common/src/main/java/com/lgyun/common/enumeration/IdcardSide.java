package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 身份证正反面
 */
@Getter
@AllArgsConstructor
public enum IdcardSide {
    //值为小写，不可改动
    FRONT("front", "身份证正面"),
    BACK("back", "身份证反面");

    private final String value;
    private final String desc;

}