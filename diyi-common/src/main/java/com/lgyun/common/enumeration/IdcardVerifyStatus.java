package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 身份验证状态
 */
@Getter
public enum IdcardVerifyStatus {
    toVerify("toVerify", "未验证"),
    verifyPass("verifyPass", "验证通过"),
    verifyFail("verifyFail", "验证未通过");

    private String value;
    private String desc;

    IdcardVerifyStatus(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}