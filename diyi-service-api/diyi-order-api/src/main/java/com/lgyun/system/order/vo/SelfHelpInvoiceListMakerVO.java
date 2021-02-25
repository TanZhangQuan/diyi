package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePrintState;
import com.lgyun.common.enumeration.MakerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceListMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "开票人身份类别")
    private MakerType makerType;

    @ApiModelProperty(value = "个体户/个独名称")
    private String individualName;

    @ApiModelProperty(value = "开票类目")
    private String invoiceType;

    @ApiModelProperty(value = "增值税税率")
    private BigDecimal valueAddedTaxRate;

    @ApiModelProperty(value = "价税合计额")
    private BigDecimal chargeMoneyNum;

    @ApiModelProperty(value = "开票手续费")
    private BigDecimal serviceInvoiceFee;

    @ApiModelProperty(value = "身份验证费")
    private BigDecimal idendityConfirmFee;

    @ApiModelProperty(value = "需支付服务商税费=价税合计额*服务税费率+开票手续费+身份验证费")
    private BigDecimal payProviderFee;

    @ApiModelProperty(value = "开票状态")
    private InvoicePrintState invoicePrintState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
