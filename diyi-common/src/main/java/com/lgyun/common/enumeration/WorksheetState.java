package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 工单状态
 */
@Getter
public enum WorksheetState {
    NORMAL("NORMAL", "正常"),
    INVALID("INVALID", "已作废");

    private String value;
    private String desc;

    WorksheetState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

}