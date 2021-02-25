package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SummaryInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户清单(多个用逗号隔开)")
    @NotBlank(message = "请输入商户清单id")
    private String payEnterpriseIds;

    @ApiModelProperty(value = "发票")
    @NotNull(message = "请上传发票")
    private String companyInvoiceUrl;

    @ApiModelProperty(value = "总完税证明")
    @NotNull(message = "请上传总完税证明")
    private String makerTaxUrl;

    @ApiModelProperty(value = "清单式完税凭证")
    private String makerTaxListUrl;

}
