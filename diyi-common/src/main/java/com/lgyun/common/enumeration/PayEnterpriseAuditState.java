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
public enum PayEnterpriseAuditState {
    EDITING("EDITING", "商户编辑中"),
    SUBMITED("SUBMITED", "商户已提交"),
    APPROVED("APPROVED", "服务商/管理中心审核通过"),
    REJECTED("REJECTED", "服务商/管理中心已驳回");

    private final String value;
    private final String desc;
}
