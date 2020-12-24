package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class WorksheetXiaoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "工单创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetMakerId;

    @ApiModelProperty(value = "工单ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long worksheetId;

    @ApiModelProperty(value = "商户ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    @ApiModelProperty(value = "商户姓名")
    private String enterpriseName;

    @ApiModelProperty(value = "工单编号")
    private String worksheetNo;

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

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "工单创客的状态")
    private WorksheetMakerState worksheetMakerState;

    @ApiModelProperty(value = "验收金额")
    private BigDecimal checkMoney;

    @ApiModelProperty(value = "工作成果说明")
    private String achievementDesc;

    @ApiModelProperty(value = "工作成果附件")
    private String achievementFiles;

    @ApiModelProperty(value = "创客身份")
    private MakerType makerType;

    @ApiModelProperty(value = "工单状态")
    private WorksheetState worksheetState;
}
