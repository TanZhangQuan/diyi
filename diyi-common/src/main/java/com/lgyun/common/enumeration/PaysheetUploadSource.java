package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 确认函上传来源
 */
@Getter
@AllArgsConstructor
public enum PaysheetUploadSource {
    PLATFORM("PLATFORM", "平台"),
    ENTERPRISE("ENTERPRISE", "外包企业");

    private final String value;
    private final String desc;

}