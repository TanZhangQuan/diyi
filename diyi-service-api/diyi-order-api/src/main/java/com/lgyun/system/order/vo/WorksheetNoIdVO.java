package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class WorksheetNoIdVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单名称")
    private String worksheetName;

    @ApiModelProperty(value = "上限人数")
    private Integer upPersonNum;

    @ApiModelProperty(value = "工作天数")
    private Integer workDays;

    @ApiModelProperty(value = "最低费用")
    private BigDecimal worksheetFeeLow;

    @ApiModelProperty(value = "最高费用")
    private BigDecimal worksheetFeeHigh;

    @ApiModelProperty(value = "工单类型")
    private WorksheetType worksheetType;

    @ApiModelProperty(value = "工单模式")
    private WorksheetMode worksheetMode;

    @ApiModelProperty(value = "创客身份")
    private MakerType makerType;

    @ApiModelProperty(value = "工单状态")
    private WorksheetState worksheetState;

    @ApiModelProperty(value = "完毕日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date finishDate;

    @ApiModelProperty(value = "说明")
    private String worksheetMemo;

    @ApiModelProperty(value = "图文说明")
    private String worksheetDescFiles;

}
