package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author .
 * @date 2020/7/28.
 * @time 18:40.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum  AuditState {
    EDITING("EDITING", "编辑中"),
    REJECTED("REJECTED", "已驳回"),
    APPROVED("APPROVED", "审核通过");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
