package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * @author jun.
 * @date 2020/7/13.
 * @time 15:07.
 */
@Getter
public enum InvoiceAuditState {
    NOTREVIEWED("NOTREVIEWED", "未审核"),
    NOAPPROVED("NOAPPROVED", "审核不通过"),
    APPROVED("APPROVED", "审核通过");

    private String value;
    private String desc;

    InvoiceAuditState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
