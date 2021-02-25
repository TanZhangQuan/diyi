package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自主开票主表ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "自主开票申请表ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "商户名字")
    private String enterpriseName;

    @ApiModelProperty(value = "开票清单")
    private String listFile;

    @ApiModelProperty(value = "众包支付模式")
    private String payType;

    @ApiModelProperty(value = "价税合计额")
    private String talChargeMoneyNum;

    @ApiModelProperty(value = "状态")
    private String currentState;

    @ApiModelProperty(value = "创建时间")
    private String createTime;
}
