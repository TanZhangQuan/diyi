package com.lgyun.system.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "交付支付验收单DTO")
public class AcceptSheetAndCsListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客/开票人名称")
    private String name;

    @ApiModelProperty(value = "交付支付验收单开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty(value = "交付支付验收单结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
