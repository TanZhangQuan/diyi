package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 协议类型
 */
@Getter
public enum AgreementType {
    INTCROSOUCOOAGR("INTCROSOUCOOAGR", "互联网众包项目合作协议"),
    CROSOUTHICOOAGR("CROSOUTHICOOAGR", "众包-三方合作协议"),
    CROSOUTHISERORD("CROSOUTHISERORD", "众包-三方服务订单"),
    GENSOUBOTHCOOAGR("GENSOUBOTHCOOAGR", "总包-双方合作协议"),
    GENSOUBOTHSERORD("GENSOUBOTHSERORD", "总包-双方服务订单"),
    SUBSOUTHICOOAGR("SUBSOUTHICOOAGR", "分包-三方合作协议"),
    SUBSOUTHISERORD("SUBSOUTHISERORD", "分包-三方服务订单"),
    SUBSOUBOTHCOOAGR("SUBSOUBOTHCOOAGR", "分包-双方合作协议"),
    SUBSOUBOTHSERORD("SUBSOUBOTHSERORD", "分包-双方服务订单"),
    LETAUTINDTAXMAT("LETAUTINDTAXMAT", "单独税务事项委托授权书"),
    POWATTSEPPAY("POWATTSEPPAY", "单独支付事项委托授权书");

    private String value;
    private String desc;

    AgreementType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}