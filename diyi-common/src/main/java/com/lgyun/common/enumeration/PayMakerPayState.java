package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分包支付明细支付状态
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum PayMakerPayState {
    TOPAY("TOPAY", "总包待支付"),
    ENTERPRISETOPAY("ENTERPRISETOPAY", "商户已申请支付"),
    ENTERPRISEPAID("ENTERPRISEPAID", "总包已支付"),
    PLATFORMPAID("PLATFORMPAID", "分包已支付"),
    CONFIRMPAID("CONFIRMPAID", "创客已确认收款");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}