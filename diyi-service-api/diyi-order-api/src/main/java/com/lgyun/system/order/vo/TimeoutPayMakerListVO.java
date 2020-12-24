package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class TimeoutPayMakerListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分包支付明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

}
