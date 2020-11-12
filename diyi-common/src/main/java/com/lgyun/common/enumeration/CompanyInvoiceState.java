package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author .
 * @date 2020/11/12.
 * @time 10:15.
 */
@Getter
@AllArgsConstructor
public enum CompanyInvoiceState {
    UNOPEN("UNOPEN", "未开"),
    OPENED("OPENED", "已全额开具"),
    PARTIALLYISSUED("PARTIALLYISSUED", "已部分开具");

    private final String value;
    private final String desc;
}
