package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.ApplyScope;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "添加或编辑服务商发票类目DTO")
public class AddOrUpdateProviderInvoiceCatalogDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商发票类目ID")
    private Long invoiceCatalogId;

    @ApiModelProperty(value = "应用范围", notes = "com.lgyun.common.enumeration.ApplyScope")
    @NotNull(message = "请选择应用范围")
    private ApplyScope applyScope;

    @ApiModelProperty(value = "发票类目名称")
    @NotBlank(message = "请输入发票类目名称")
    private String invoiceCatalogName;

    @ApiModelProperty(value = "设置说明")
    private String setDesc;

}
