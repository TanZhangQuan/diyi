package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工作成果状态
 */
@Getter
@AllArgsConstructor
public enum WorkAchievementState {
    TOCHECK("TOCHECK", "待验收"),
    CHECKSUCCESS("CHECKSUCCESS", "验收通过"),
    CHECKFAIL("CHECKFAIL", "验收不通过");

    private final String value;
    private final String desc;

}