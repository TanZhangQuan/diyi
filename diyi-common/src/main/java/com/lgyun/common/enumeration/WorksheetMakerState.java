package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 工单创客的状态：1待提交，2待验证，3验证通过，4验证失败
 * @author tzq
 * @date 2020/7/8.
 * @time 10:27.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum WorksheetMakerState {
    SUBMITTED("SUBMITTED", "待提交"),
    VERIFIED("VERIFIED", "待验证"),
    VALIDATION("VALIDATION", "验证通过"),
    FAILED("FAILED", "验证失败");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
