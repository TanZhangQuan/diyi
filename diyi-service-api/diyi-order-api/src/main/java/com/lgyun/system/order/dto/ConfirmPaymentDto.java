package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.PaymentType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 确认支付
 * @author jun.
 * @date 2020/7/14.
 * @time 10:36.
 */
@Data
public class ConfirmPaymentDto implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票收费
     */
    @NotBlank(message = "请输入自助开票收费ID")
    private Long handPayId;

    /**
     * 支付说明
     */
    @NotBlank(message = "请输入支付说明")
    private String payDesc;
    /**
     * 支付回单
     */
    @NotBlank(message = "请上传支付回单")
    private String payCertificate;

    /**
     * 支付选择
     */
    private PaymentType paymentType = PaymentType.BANKCARDPAYMENT;
}
