package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class PayMakerListWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分包支付明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "到手服务费")
    private BigDecimal makerNetIncome;

    @ApiModelProperty(value = "综合税+费")
    private BigDecimal makerTaxFee;

    @ApiModelProperty(value = "首次身份验证费")
    private BigDecimal auditFee;

    @ApiModelProperty(value = "第三方支付手续费")
    private BigDecimal payFee;

    @ApiModelProperty(value = "企业总支付金额")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "综合税费率")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "是否已开交付支付验收单")
    private boolean boolAcceptSheet;

    @ApiModelProperty(value = "支付回单")
    private String makerPayReceiptUrls;

    @ApiModelProperty(value = "发票")
    private String invoiceUrl;

    @ApiModelProperty(value = "税票")
    private String taxUrl;

}
