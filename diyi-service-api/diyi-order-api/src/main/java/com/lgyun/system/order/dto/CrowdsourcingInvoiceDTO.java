package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class CrowdsourcingInvoiceDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票详情ID")
    @NotNull(message = "请选择自助开票详情")
    private Long selfHelpInvoiceDetailId;

    @ApiModelProperty(value = "发票")
    @NotBlank(message = "请上传发票")
    private String invoiceScanPictures;

    @ApiModelProperty(value = "税票")
    private String taxScanPictures;
}
