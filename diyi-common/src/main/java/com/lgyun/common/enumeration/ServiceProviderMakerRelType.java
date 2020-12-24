package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 关联类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum ServiceProviderMakerRelType {
    TOTALSUBCONTRACTREL("TOTALSUBCONTRACTREL", "总包+分包支付关联"),
    CROWDSOURCINGREL("CROWDSOURCINGREL", "众包代开票关联");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}