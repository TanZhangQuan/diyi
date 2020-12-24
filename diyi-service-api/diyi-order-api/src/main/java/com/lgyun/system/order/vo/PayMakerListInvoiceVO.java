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
public class PayMakerListInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分包支付明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

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

    @ApiModelProperty(value = "发票")
    private String makerVoiceUrl;

    @ApiModelProperty(value = "完税证明")
    private String makerTaxUrl;

}
