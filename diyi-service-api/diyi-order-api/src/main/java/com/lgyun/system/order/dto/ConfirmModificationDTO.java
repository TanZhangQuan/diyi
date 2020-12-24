package com.lgyun.system.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class ConfirmModificationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自助开票ID")
    @NotNull(message = "请选择自助开票")
    private Long selfHelpInvoiceId;

    @ApiModelProperty(value = "XXXXX")
    private List<ModificationDTO> list;
}
