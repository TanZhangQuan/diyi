package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 自助开票状态
 * @author .
 * @date 2020/8/8.
 * @time 10:41.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum  SelfHelpInvoiceApplyState {
    NOTAPPLY("NOTAPPLY", "编辑中"),
    APPLYING("APPLYING", "申请中"),
    AUDITING("AUDITING", "审核中"),
    TOPAY("TOPAY", "已审核待付费"),
    PAID("PAID", "已付费开票中"),
    REJECTED("REJECTED", "已驳回"),
    INVOICED("INVOICED", "已开票结束");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
