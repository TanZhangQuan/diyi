package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceSerProVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "购买方")
    private String enterpriseName;

    @ApiModelProperty(value = "发票扫描件（多张）")
    private String invoiceScanPictures;

    @ApiModelProperty(value = "税票扫描件（多张）")
    private String taxScanPictures;

    @ApiModelProperty(value = "自助开票状态")
    private SelfHelpInvoiceApplyState currentState;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
