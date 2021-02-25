package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 确认函上传来源
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum PaysheetUploadSource {
    PLATFORM("PLATFORM", "平台"),
    ENTERPRISE("ENTERPRISE", "外包企业");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}