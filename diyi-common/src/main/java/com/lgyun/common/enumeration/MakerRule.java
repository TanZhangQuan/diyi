package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 个人创客规则要求
 */
@Getter
@AllArgsConstructor
public enum MakerRule {
    IDCARDVERIFY("IDCARDVERIFY", "身份证验证"),
    EMPOWERVIDEO("EMPOWERVIDEO", "授权视频"),
    FACEVERIFY("FACEVERIFY", "活体验证"),
    BANKCARDVERIFY("BANKCARDVERIFY", "银行卡验证"),
    PHONENUMBERVERIFY("PHONENUMBERVERIFY", "手机号验证"),
    EMPOWERSIGN("EMPOWERSIGN", "创客授权书");

    private final String value;
    private final String desc;

}
