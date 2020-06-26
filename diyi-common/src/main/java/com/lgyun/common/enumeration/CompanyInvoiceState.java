package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 平台给企业开票状态
 */
@Getter
public enum CompanyInvoiceState {
    unOpen("unOpen", "未开"),
    opened("opened", "已开");

    private String value;
    private String desc;

    CompanyInvoiceState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}