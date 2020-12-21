package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 对象身份
 *
 * @author tzq
 * @date 2020/7/22.
 * @time 9:26.
 */
@Getter
@AllArgsConstructor
public enum ObjectType {
    MAKERPEOPLE("MAKERPEOPLE", "创客"),
    ENTERPRISEPEOPLE("ENTERPRISEPEOPLE", "商户"),
    SERVICEPEOPLE("SERVICEPEOPLE", "服务商"),
    RELBUREAUPEOPLE("RELBUREAUPEOPLE", "相关局"),
    AGENTMAINPEOPLE("AGENTMAINPEOPLE", "渠道商"),
    PARTNERPEOPLE("PARTNERPEOPLE", "合伙人");

    private final String value;
    private final String desc;
}
