package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "XXXXX")
public class AcceptPaysheetCsDetailMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "自主开票详情ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "商户名称")
    private String enterpriseName;

    @ApiModelProperty(value = "价税合计额")
    private BigDecimal chargeMoneyNum;

    @ApiModelProperty(value = "服务开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeStart;

    @ApiModelProperty(value = "服务结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date serviceTimeEnd;

    @ApiModelProperty(value = "验收单")
    private String acceptPaysheetCsUrl;

}
