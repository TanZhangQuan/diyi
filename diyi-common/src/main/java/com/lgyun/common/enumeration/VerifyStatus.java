package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 身份验证状态
 */
@Getter
public enum VerifyStatus {
    TOVERIFY("TOVERIFY", "未验证"),
    VERIFYPASS("VERIFYPASS", "验证通过"),
    VERIFYFAIL("VERIFYFAIL", "验证未通过");

    private String value;
    private String desc;

    VerifyStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}