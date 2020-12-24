package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class SelfInvoiceListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "申请商户的ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applyEnterpriseId;

    @ApiModelProperty(value = "申请创客的ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applyMakerId;

    @ApiModelProperty(value = "申请商户的名称")
    private String applyEnterpriseName;

    @ApiModelProperty(value = "申请创客的名字")
    private String applyMakerName;

    @ApiModelProperty(value = "清单")
    private String listFile;

    @ApiModelProperty(value = "众包支付模式")
    private CrowdSourcingPayType payType;

    @ApiModelProperty(value = "总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算")
    private BigDecimal totalPayProviderFee;

    @ApiModelProperty(value = "申请状态")
    private SelfHelpInvoiceApplyState applyState;

    @ApiModelProperty(value = "申请时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
