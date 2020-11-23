package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对象身份1、创客本人2、商户人员3、服务商人员4、相关局人员5、渠道商人员6、合伙人本人
 * @author tzq
 * @date 2020/7/22.
 * @time 9:26.
 */
@Getter
@AllArgsConstructor
public enum ObjectType {
    MAKERPEOPLE("MAKERPEOPLE", "创客本人"),
    ENTERPRISEPEOPLE("ENTERPRISEPEOPLE", "商户人员"),
    SERVICEPEOPLE("SERVICEPEOPLE", "服务商人员"),
    RELBUREAUPEOPLE("RELBUREAUPEOPLE", "相关局人员"),
    AGENTMAINPEOPLE("AGENTMAINPEOPLE", "渠道商人员"),
    PARTNERPEOPLE("PARTNERPEOPLE", "合伙人人员");

    private final String value;
    private final String desc;
}
