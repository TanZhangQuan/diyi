package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.SelfHelpInvoiceSpApplyState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceListByServiceProviderVO implements Serializable {

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "众包支付模式")
    private CrowdSourcingPayType payType;

    @ApiModelProperty(value = "开票类目")
    private String invoiceTypes;

    @ApiModelProperty(value = "总需支付服务商税费")
    private BigDecimal totalPayProviderFee;

    @ApiModelProperty(value = "业务合同(多张)")
    private String businessContractUrls;

    @ApiModelProperty(value = "支付回单(多张)")
    private String flowContractUrls;

    @ApiModelProperty(value = "交付支付验收单(多张)")
    private String acceptPaysheetUrls;

    @ApiModelProperty(value = "快递单号")
    private String expressNo;

    @ApiModelProperty(value = "快递公司")
    private String expressCompanyName;

    @ApiModelProperty(value = "自助开票服务商状态")
    private SelfHelpInvoiceSpApplyState applyState;

    @ApiModelProperty(value = "开票时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
