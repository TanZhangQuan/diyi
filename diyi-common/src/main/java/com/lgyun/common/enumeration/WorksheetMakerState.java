package com.lgyun.common.enumeration;

import lombok.Getter;

/**
 * 工单创客的状态：1待提交，2待验证，3验证通过，4验证失败
 * @author jun.
 * @date 2020/7/8.
 * @time 10:27.
 */
@Getter
public enum WorksheetMakerState {
    SUBMITTED("SUBMITTED", "待提交"),
    VERIFIED("VERIFIED", "待验证"),
    VALIDATION("VALIDATION", "验证通过"),
    FAILED("FAILED", "验证失败");


    private String value;
    private String desc;

    WorksheetMakerState(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }
}
