package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author .
 * @date 2020/11/12.
 * @time 10:15.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum CompanyInvoiceState {
    UNOPEN("UNOPEN", "未开"),
    OPENED("OPENED", "已全额开具"),
    PARTIALLYISSUED("PARTIALLYISSUED", "已部分开具");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
