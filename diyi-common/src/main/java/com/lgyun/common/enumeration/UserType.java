package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户类型
 *
 * @author tzq
 */
@Getter
@AllArgsConstructor
public enum UserType {
    MAKER("MAKER", "创客"),
    ADMIN("ADMIN", "管理员"),
    PARTNER("PARTNER", "合伙人"),
    AGENTMAIN("AGENTMAIN","渠道商"),
    TAXBUREAU("TAXBUREAU","税局"),
    ENTERPRISE("ENTERPRISE", "商户"),
    PAYMENTAGENCY("PAYMENTAGENCY","支付机构"),
    SERVICEPROVIDER("SERVICEPROVIDER", "服务商"),
    INDUSTRIALPARKS("INDUSTRIALPARKS","产业园区"),
    MARKETSUPERVISION("MARKETSUPERVISION","市场监督管理局");

    private final String value;
    private final String desc;

}
