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
public class InvoiceEnterpriseVO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "支付清单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @ApiModelProperty(value = "创客支付ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    @ApiModelProperty(value = "商户名字")
    private String enterpriseName;

    @ApiModelProperty(value = "服务商名字")
    private String serviceProviderName;

    @ApiModelProperty(value = "工单类型")
    private String worksheetType;

    @ApiModelProperty(value = "金额")
    private BigDecimal payToPlatformAmount;

    @ApiModelProperty(value = "发票")
    private String makerVoiceUrl;

    @ApiModelProperty(value = "完税证明")
    private String makerTaxUrl;

    @ApiModelProperty(value = "工单名称")
    private String worksheetName;

    @ApiModelProperty(value = "XXXXX")
    private String worksheetMode;

    @ApiModelProperty(value = "发布日期")
    private String createTime;

    @ApiModelProperty(value = "工单编号")
    private String worksheetNo;

    @ApiModelProperty(value = "工作成果附件")
    private String achievementFiles;

    @ApiModelProperty(value = "验收金额")
    private String checkMoney;

    @ApiModelProperty(value = "创客数")
    private Integer makerNum;
}
