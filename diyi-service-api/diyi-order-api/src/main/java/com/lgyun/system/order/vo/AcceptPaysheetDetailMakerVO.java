package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PayMakerPayState;
import com.lgyun.common.enumeration.WorksheetMode;
import com.lgyun.common.enumeration.WorksheetType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class AcceptPaysheetDetailMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分包支付明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "支付总额")
    private BigDecimal makerNetIncome;

    @ApiModelProperty(value = "支付状态")
    private PayMakerPayState payState;

    @ApiModelProperty(value = "服务开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeStart;

    @ApiModelProperty(value = "服务结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeEnd;

    @ApiModelProperty(value = "验收单")
    private String acceptPaysheetUrl;

    @ApiModelProperty(value = "工单名称")
    private String worksheetName;

    @ApiModelProperty(value = "类型")
    private WorksheetType worksheetType;

    @ApiModelProperty(value = "模式")
    private WorksheetMode worksheetMode;

    @ApiModelProperty(value = "发布时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty(value = "工单编号")
    private String worksheetNo;

    @ApiModelProperty(value = "验收金额")
    private BigDecimal checkMoney;

    @ApiModelProperty(value = "工作成果说明")
    private String achievementDesc;

    @ApiModelProperty(value = "工作成果附件")
    private String achievementFiles;

}
