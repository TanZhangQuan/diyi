package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.WorksheetType;
import com.lgyun.common.enumeration.WorksheetState;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "工单列表DTO")
public class WorksheetFinishedListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单编号")
    private String worksheetNo;

    @ApiModelProperty(value = "工单名称")
    private String worksheetName;

    @ApiModelProperty(value = "工单类型", notes = "com.lgyun.common.enumeration.WorksheetType")
    private WorksheetType worksheetType;

    @ApiModelProperty(value = "创客类型", notes = "com.lgyun.common.enumeration.MakerType")
    private MakerType makerType;

    @ApiModelProperty(value = "工单状态", notes = "com.lgyun.common.enumeration.WorksheetState")
    private WorksheetState worksheetState;

}
