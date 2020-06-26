package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 确认函上传来源
 */
@Getter
public enum PaysheetUploadSource {
    platform("platform", "平台"),
    enterprise("enterprise", "外包企业");

    private String value;
    private String desc;

    PaysheetUploadSource(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}