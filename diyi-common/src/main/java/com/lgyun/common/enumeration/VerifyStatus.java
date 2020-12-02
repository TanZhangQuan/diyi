package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证状态
 */
@Getter
@AllArgsConstructor
public enum VerifyStatus {
    TOVERIFY("TOVERIFY", "未验证"),
    VERIFYPASS("VERIFYPASS", "验证通过"),
    VERIFYFAIL("VERIFYFAIL", "验证未通过");

    private final String value;
    private final String desc;

}