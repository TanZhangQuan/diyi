package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 众包支付通路
 */
@Getter
public enum CrowdSourcePayPath {
    TONGLIANPAY("TONGLIANPAY", "通联支付代发"),
    ZHAOSHANGPAY("ZHAOSHANGPAY", "招商银行代发"),
    SYSINTPAY("SYSINTPAY", "系统集成代发"),
    PLACOLPAY("PLACOLPAY", "平台代收代付"),
    PLAPREPAY("PLAPREPAY", "平台预存支付");

    private String value;
    private String desc;

    CrowdSourcePayPath(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}