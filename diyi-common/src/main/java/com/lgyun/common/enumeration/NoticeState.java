package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  NoticeState {
    BEEDITING("BEEDITING", "编辑中"),
    HAVEREAD("HAVEREAD", "已阅读"),
    CANCELLED("CANCELLED", "已作废"),
    PUBLISHED("PUBLISHED", "已发布");
    private final String value;
    private final String desc;
}
