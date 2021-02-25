package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.InvoicePrintState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "开票类目")
    private String invoiceType;

    @ApiModelProperty(value = "开票状态")
    private InvoicePrintState invoicePrintState;

    @ApiModelProperty(value = "税种")
    private BizType bizType;

    @ApiModelProperty(value = "总价税合计额")
    private BigDecimal chargeMoneyNum;

    @ApiModelProperty(value = "流水回单")
    private String flowContractUrl;

    @ApiModelProperty(value = "业务合同")
    private String businessContractUrl;

    @ApiModelProperty(value = "开票时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

}
