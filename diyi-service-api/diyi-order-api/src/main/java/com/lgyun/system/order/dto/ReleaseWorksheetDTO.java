package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorksheetMode;
import com.lgyun.common.enumeration.WorksheetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "发布工单DTO")
public class ReleaseWorksheetDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单名称")
    @NotBlank(message = "请输入工单名称")
    private String worksheetName;

    @ApiModelProperty(value = "上限人数")
    @NotNull(message = "请输入上限人数")
    @Min(value = 1, message = "上限人数不能小于1")
    private Integer upPersonNum;

    @ApiModelProperty(value = "工作天数")
    @NotNull(message = "请输入工作天数")
    @Min(value = 1, message = "工作天数不能小于1")
    private Integer workDays;

    @ApiModelProperty(value = "最低费用")
    @NotNull(message = "请输入最低费用")
    @Min(value = 0, message = "最低费用不能小于0")
    private BigDecimal worksheetFeeLow;

    @ApiModelProperty(value = "最高费用")
    @NotNull(message = "请输入最高费用")
    @Min(value = 0, message = "最高费用不能小于0")
    private BigDecimal worksheetFeeHigh;

    @ApiModelProperty(value = "工单类型", notes = "com.lgyun.common.enumeration.WorksheetType")
    @NotNull(message = "请选择工单类型")
    private WorksheetType worksheetType;

    @ApiModelProperty(value = "工单模式", notes = "com.lgyun.common.enumeration.WorksheetMode")
    @NotNull(message = "请选择工单模式")
    private WorksheetMode worksheetMode;

    @ApiModelProperty(value = "创客身份", notes = "com.lgyun.common.enumeration.MakerType")
    @NotNull(message = "请选择创客身份")
    private MakerType makerType;

    @ApiModelProperty(value = "创客")
    private String MakerIds;

    @ApiModelProperty(value = "工单说明")
    private String worksheetMemo;

    @ApiModelProperty(value = "工单图文说明")
    private String worksheetDescFiles;
}
