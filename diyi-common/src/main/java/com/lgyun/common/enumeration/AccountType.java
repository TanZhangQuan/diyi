package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum AccountType {
    BANK("BANK", "银行账户"),
    THIRDPAY("THIRDPAY", "第三方支付账户"),
    INDIVIDUALACCOUNT("INDIVIDUALACCOUNT", "个人账户"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}