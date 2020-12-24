package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 个人创客规则要求
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum MakerRule {
    IDCARDVERIFY("IDCARDVERIFY", "身份证验证"),
    EMPOWERVIDEO("EMPOWERVIDEO", "授权视频"),
    FACEVERIFY("FACEVERIFY", "活体验证"),
    BANKCARDVERIFY("BANKCARDVERIFY", "银行卡验证"),
    PHONENUMBERVERIFY("PHONENUMBERVERIFY", "手机号验证"),
    EMPOWERSIGN("EMPOWERSIGN", "创客授权书");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
