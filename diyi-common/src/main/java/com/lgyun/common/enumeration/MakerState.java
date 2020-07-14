package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 创客账号状态
 */
@Getter
@AllArgsConstructor
public enum MakerState {
    NORMAL("NORMAL", "正常状态"),
    FREEZE("FREEZE", "冻结状态"),
    ILLEGAL("ILLEGAL", "非法状态");

    private final String value;
    private final String desc;

}