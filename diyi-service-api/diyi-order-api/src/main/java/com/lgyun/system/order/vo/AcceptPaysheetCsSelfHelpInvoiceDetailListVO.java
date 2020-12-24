package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class AcceptPaysheetCsSelfHelpInvoiceDetailListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自主开票明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "创客/开票人姓名")
    private String name;

    @ApiModelProperty(value = "增值税税率")
    private BigDecimal valueAddedTaxRate;

    @ApiModelProperty(value = "价税合计额")
    private BigDecimal chargeMoneyNum;

    @ApiModelProperty(value = "开票手续费")
    private BigDecimal serviceInvoiceFee;

    @ApiModelProperty(value = "身份验证费")
    private BigDecimal idendityConfirmFee;

    @ApiModelProperty(value = "需支付服务商税费")
    private BigDecimal payProviderFee;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
