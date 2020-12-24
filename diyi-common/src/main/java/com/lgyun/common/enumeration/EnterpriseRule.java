package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 商户规则要求
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum EnterpriseRule {
    BIZLICENCE("BIZLICENCE", "营业执照"),
    COMMITMENTLETTER("COMMITMENTLETTER", "商户业务真实性承诺函");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
