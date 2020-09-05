package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 开票状态
 */
@Getter
@AllArgsConstructor
public enum InvoicePrintState {
    TOAPPLY("TOAPPLY", "待申请"),
    APPLYING("APPLYING", "申请中"),
    INVOICEING("INVOICEING", "开票中"),
    INVOICESUCCESS("INVOICESUCCESS", "已开票"),
    INVOICEFAIL("INVOICEFAIL", "开票失败");

    private final String value;
    private final String desc;

}