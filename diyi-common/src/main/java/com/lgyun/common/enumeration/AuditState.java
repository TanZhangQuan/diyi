package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author .
 * @date 2020/7/28.
 * @time 18:40.
 */
@Getter
@AllArgsConstructor
public enum  AuditState {
    EDITING("EDITING", "编辑中"),
    REJECTED("REJECTED", "已驳回"),
    APPROVED("APPROVED", "审核通过");

    private final String value;
    private final String desc;
}
