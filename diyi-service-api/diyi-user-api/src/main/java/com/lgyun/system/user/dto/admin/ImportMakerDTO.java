package com.lgyun.system.user.dto.admin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "导入创客参数")
public class ImportMakerDTO {
    @ApiModelProperty(value = "商户编号", notes = "商户编号")
    @NotNull(message = "请选择商户")
    private Long enterpriseId;

    @ApiModelProperty(value = "创客列表", notes = "创客列表")
    @Valid
    private List<ImportMakerListDTO> importMakerDTOS;
}
