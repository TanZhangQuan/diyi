package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 相关局类型
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum RelBureauType {
    TAXBUREAU("TAXBUREAU", "税局"),
    MARKETSUPERVISION("MARKETSUPERVISION", "市场监督管理局"),
    INDUSTRIALPARKS("INDUSTRIALPARKS", "产业园区"),
    PAYMENTAGENCY("PAYMENTAGENCY", "支付机构");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
