package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.PayMakerPayState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class PayMakerListMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分包支付明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "创客身份")
    private MakerType makerType;

    @ApiModelProperty(value = "个体户/个独名称")
    private String individualName;

    @ApiModelProperty(value = "创客到手=服务外包费-创客税费=服务外包费*服务税费率")
    private BigDecimal makerNetIncome;

    @ApiModelProperty(value = "服务税费率")
    private BigDecimal serviceRate;

    @ApiModelProperty(value = "服务税费=服务外包费*服务税费率")
    private BigDecimal makerTaxFee;

    @ApiModelProperty(value = "服务外包费=创客到手/(1-服务税费率)")
    private BigDecimal makerNeIncome;

    @ApiModelProperty(value = "企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费")
    private BigDecimal enterpriseBusinessAnnualFee;

    @ApiModelProperty(value = "身份验证费")
    private BigDecimal auditFee;

    @ApiModelProperty(value = "第三方支付手续费")
    private BigDecimal payFee;

    @ApiModelProperty(value = "企业总支付额价税合计=服务外包费+身份验证费/个体户年费+第三方支付手续费")
    private BigDecimal totalFee;

    @ApiModelProperty(value = "支付状态")
    private PayMakerPayState payState;

    @ApiModelProperty(value = "完税证明开票状态")
    private InvoiceState makerTaxState;

    @ApiModelProperty(value = "发票开票状态")
    private InvoiceState makerInvoiceState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
