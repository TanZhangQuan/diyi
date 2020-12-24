package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "服务商上传发票税票DTO")
public class SelfHelpInvoiceDetailInvoiceTaxDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票明细ID")
    @NotNull(message = "请输入自助开票明细编号")
    private Long selfHelpInvoiceDetailId;

    @ApiModelProperty(value = "发票(多张)")
    @NotBlank(message = "请上传发票")
    private String InvoiceScanPictures;

    @ApiModelProperty(value = "税票(多张)")
    private String TaxScanPictures;

}
