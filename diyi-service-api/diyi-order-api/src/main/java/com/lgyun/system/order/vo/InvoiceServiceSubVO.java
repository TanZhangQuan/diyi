package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "服务商查询未开票分包发票VO")
public class InvoiceServiceSubVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "开票清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "是否有支付回单")
    private String isPayEnterpriseReceipt;

    @ApiModelProperty(value = "分包开票状态")
    private InvoiceState subcontractingInvoiceState;

    @ApiModelProperty(value = "创客发票开票类别")
    private MakerInvoiceType makerInvoiceType;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
