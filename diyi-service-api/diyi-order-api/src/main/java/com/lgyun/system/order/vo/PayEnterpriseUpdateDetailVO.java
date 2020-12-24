package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.MakerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class PayEnterpriseUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "服务商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    @ApiModelProperty(value = "服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "创客类型")
    private MakerType makerType;

    @ApiModelProperty(value = "支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "工单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    @ApiModelProperty(value = "工单编号")
    private String worksheetNo;

    @ApiModelProperty(value = "工单名称")
    private String worksheetName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date worksheetCreateTime;

    @ApiModelProperty(value = "企业总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额")
    private BigDecimal payToPlatformAmount;

    @ApiModelProperty(value = "服务税费总额=服务外包费总额*服务税费率")
    private BigDecimal totalTaxFee;

    @ApiModelProperty(value = "创客到手总额")
    private BigDecimal totalMakerNetIncome;

    @ApiModelProperty(value = "服务税费率")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "服务外包费总额")
    private BigDecimal sourcingAmount;

    @ApiModelProperty(value = "企业年费总额")
    private BigDecimal enterpriseBusinessAnnualFee;

    @ApiModelProperty(value = "身份验证费总额")
    private BigDecimal identifyFee;

    @ApiModelProperty(value = "第三方支付手续费总额")
    private BigDecimal serviceFee;

    @ApiModelProperty(value = "创客数")
    private Integer makerNum;

    @ApiModelProperty(value = "支付说明")
    private String payMemo;

    @ApiModelProperty(value = "支付回单(多张)")
    private String enterprisePayReceiptUrls;

}
