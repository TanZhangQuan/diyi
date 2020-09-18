package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务商资格类型
 */
@Getter
@AllArgsConstructor
public enum CertificateType {
    TAXRELEVANT("TAXRELEVANT", "税务相关"),
    INDUSTRYCOMMERCERELEVANT("INDUSTRYCOMMERCERELEVANT", "工商相关"),
    PARKRELEVANT("PARKRELEVANT", "园区相关"),
    GOVERNMENTRELEVANT("GOVERNMENTRELEVANT", "政府相关"),
    SOCIALORGANIZATIONRELEVANT("SOCIALORGANIZATIONRELEVANT", "社会组织相关"),
    OTHERRELEVANT("OTHERRELEVANT", "其他");

    private final String value;
    private final String desc;

}