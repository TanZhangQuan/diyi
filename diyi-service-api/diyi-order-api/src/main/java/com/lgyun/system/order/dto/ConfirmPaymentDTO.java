package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.PaymentType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "确认支付DTO")
public class ConfirmPaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票收费ID")
    @NotNull(message = "请输入自助开票收费ID")
    private Long handPayId;

    @ApiModelProperty(value = "支付说明")
    @NotBlank(message = "请输入支付说明")
    private String payDesc;

    @ApiModelProperty(value = "支付回单")
    @NotBlank(message = "请上传支付回单")
    private String payCertificate;

    @ApiModelProperty(value = "支付方式", notes = "com.lgyun.common.enumeration.PaymentType")
    @NotNull(message = "请选择支付方式")
    private PaymentType paymentType;
}
