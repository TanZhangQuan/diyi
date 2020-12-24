package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 验证状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum VerifyStatus {
    TOVERIFY("TOVERIFY", "未验证"),
    VERIFYPASS("VERIFYPASS", "验证通过"),
    VERIFYFAIL("VERIFYFAIL", "验证未通过");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}