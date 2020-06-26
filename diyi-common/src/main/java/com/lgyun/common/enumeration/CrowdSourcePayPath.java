package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 众包支付通路
 */
@Getter
public enum CrowdSourcePayPath {
    tongLianPay("tongLianPay", "通联支付代发"),
    zhaoShangPay("zhaoShangPay", "招商银行代发"),
    sysIntPay("sysIntPay", "系统集成代发"),
    plaColPay("plaColPay", "平台代收代付"),
    plaPrePay("plaPrePay", "平台预存支付");

    private String value;
    private String desc;

    CrowdSourcePayPath(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}