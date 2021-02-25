package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class CreateCrowdsourcingInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "快递单号")
    @NotBlank(message = "请输入快递单号")
    private String expressNo;

    @ApiModelProperty(value = "快递公司名称")
    @NotBlank(message = "请输入快递公司名称")
    private String expressCompanyName;

    @ApiModelProperty(value = "自助开票ID")
    @NotNull(message = "请选择自助开票")
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "明细加发票")
    private List<CrowdsourcingInvoiceDTO> list;
}
