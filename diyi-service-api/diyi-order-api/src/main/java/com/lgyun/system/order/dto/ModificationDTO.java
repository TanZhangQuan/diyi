package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class ModificationDTO implements Serializable {

    @ApiModelProperty(value = "自主开票明细ID")
    private Long selfHelpInvoiceDetailId;

    @ApiModelProperty(value = "身份证正面")
    @NotBlank(message = "请上传身份证正面")
    private String positiveIdCard;

    @ApiModelProperty(value = "XXXXX")
    @NotBlank(message = "请上传身份证反面")
    private String backIdCard;

    @ApiModelProperty(value = "业务合同")
    @NotBlank(message = "请输入业务合同")
    private String businessContract;

    @ApiModelProperty(value = "支付回单")
    @NotBlank(message = "请输入支付回单")
    private String paymentReceipt;

}
