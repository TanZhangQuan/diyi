package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceDemand;
import com.lgyun.common.enumeration.SetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class EnterpriseProviderInvoiceCatalogListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商发票类目ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "发票类目名称")
    private String invoiceCatalogName;

    @ApiModelProperty(value = "设置人员")
    private String setPerson;

    @ApiModelProperty(value = "设置性质")
    private SetType setType;

    @ApiModelProperty(value = "开票诉求")
    private InvoiceDemand invoiceDemand;

    @ApiModelProperty(value = "开票诉求备注")
    private String invoiceDemandDesc;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
