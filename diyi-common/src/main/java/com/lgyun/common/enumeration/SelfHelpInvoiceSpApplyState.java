package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自助开票-服务商状态
 * @author .
 * @date 2020/8/8.
 * @time 10:41.
 */
@Getter
@AllArgsConstructor
public enum SelfHelpInvoiceSpApplyState {
    ALLOCATED("ALLOCATED", "已分配服务商"),
    SUBMITTED("SUBMITTED", "已提交开票中"),
    RECALLED("RECALLED", "已撤回"),
    INVOICED("INVOICED", "已开票结束");

    private final String value;
    private final String desc;
}
