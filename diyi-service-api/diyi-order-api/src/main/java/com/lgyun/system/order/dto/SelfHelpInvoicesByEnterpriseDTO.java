package com.lgyun.system.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "支付清单列表DTO")
public class SelfHelpInvoicesByEnterpriseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "开票人姓名")
    private String invoicePeopleName;

    @ApiModelProperty(value = "自助开票开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty(value = "自助开票结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
