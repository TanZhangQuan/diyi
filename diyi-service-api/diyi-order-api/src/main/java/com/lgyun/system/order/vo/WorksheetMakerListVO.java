package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class WorksheetMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     *
     */
    @ApiModelProperty(value = "名字")
    private String name;

    /**
     * 身份证
     */
    @ApiModelProperty(value = "XXXXX")
    private String idcardNo;

    /**
     * 验收金额
     */
    @ApiModelProperty(value = "XXXXX")
    private BigDecimal checkMoney;
}
