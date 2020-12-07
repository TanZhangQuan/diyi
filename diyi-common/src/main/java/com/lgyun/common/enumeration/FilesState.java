package com.lgyun.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FilesState {
    EDITING("EDITING", "编辑中"),
    PUBLISHED("PUBLISHED", "已发布"),
    HAVEREAD("HAVEREAD", "已阅读"),
    UNSHELVE("UNSHELVE", "已下架"),
    CANCELLED("CANCELLED", "已作废");

    private final String value;
    private final String desc;
}
