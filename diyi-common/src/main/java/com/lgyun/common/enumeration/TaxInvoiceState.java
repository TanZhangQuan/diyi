package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 平台给企业开票状态
 */
@Getter
public enum TaxInvoiceState {
    UNOPEN("UNOPEN", "未开"),
    OPENED("OPENED", "已开");

    private String value;
    private String desc;

    TaxInvoiceState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}