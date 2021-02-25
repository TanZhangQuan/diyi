package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 服务商资格类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CertificateType {
    TAXRELEVANT("TAXRELEVANT", "税务相关"),
    INDUSTRYCOMMERCERELEVANT("INDUSTRYCOMMERCERELEVANT", "工商相关"),
    PARKRELEVANT("PARKRELEVANT", "园区相关"),
    GOVERNMENTRELEVANT("GOVERNMENTRELEVANT", "政府相关"),
    SOCIALORGANIZATIONRELEVANT("SOCIALORGANIZATIONRELEVANT", "社会组织相关"),
    OTHERRELEVANT("OTHERRELEVANT", "其他");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}