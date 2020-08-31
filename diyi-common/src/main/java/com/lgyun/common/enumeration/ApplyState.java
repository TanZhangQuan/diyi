package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态:1，编辑中，2，审核中；3，已通过开票中；4，已驳回；5，已开票结束
 * @author .
 * @date 2020/8/8.
 * @time 10:41.
 */
@Getter
@AllArgsConstructor
public enum  ApplyState {
    EDITING("EDITING", "编辑中"),
    AUDITING("AUDITING", "审核中"),
    PASSED("PASSED", "已通过开票中"),
    REJECTED("REJECTED", "已驳回"),
    INVOICED("INVOICED", "已开票结束");

    private final String value;
    private final String desc;
}
