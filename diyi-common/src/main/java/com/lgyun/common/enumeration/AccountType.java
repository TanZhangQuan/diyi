package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账号类型
 */
@Getter
@AllArgsConstructor
public enum AccountType {
    BANK("BANK", "银行账户"),
    THIRDPAY("THIRDPAY", "第三方支付账户"),
    INDIVIDUALACCOUNT("INDIVIDUALACCOUNT", "个人账户"),
    OTHER("OTHER", "其他");

    private final String value;
    private final String desc;

}