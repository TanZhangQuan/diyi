package com.lgyun.common.enumeration;

import com.lgyun.common.annotation.SwaggerDisplayEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付类型
 * @author tzq
 * @date 2020/7/2.
 * @time 14:08.
 */
@Getter
@AllArgsConstructor
@SwaggerDisplayEnum()
public enum PaymentType {
    WECHATPAYMENT("WECHATPAYMENT", "微信支付"),
    ALIPAYPAYMENT("ALIPAYPAYMENT", "支付宝支付"),
    BANKCARDPAYMENT("BANKCARDPAYMENT", "银行卡支付"),
    CASHPAYMENT("CASHPAYMENT", "现金支付");

    private final String value;
    private final String desc;

    //不使用@ToString，手动重写，让swagger显示更好看
    @Override
    public String toString() {
        return value + ":" + desc;
    }

}
