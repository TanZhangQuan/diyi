package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum RelBureauNoticeFileState {
    EDITING("EDITING", "编辑中"),
    PUBLISHED("PUBLISHED", "已发布"),
    HAVEREAD("HAVEREAD", "已阅读"),
    CANCELLED("CANCELLED", "已作废");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
