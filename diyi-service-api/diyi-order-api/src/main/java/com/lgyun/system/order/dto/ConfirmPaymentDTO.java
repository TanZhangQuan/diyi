package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.PaymentType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 确认支付
 * @author jun.
 * @date 2020/7/14.
 * @time 10:36.
 */
@Data
public class ConfirmPaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票收费ID
     */
    @NotNull(message = "请输入自助开票收费ID")
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
    @NotNull(message = "请选择支付方式")
    private PaymentType paymentType;
}
