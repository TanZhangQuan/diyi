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
public class SelfHelpInvoiceDetailListVO implements Serializable {

    @ApiModelProperty(value = "自助开票明细ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "增值税税率")
    private BigDecimal valueAddedTaxRate;

    @ApiModelProperty(value = "价税合计额")
    private BigDecimal chargeMoneyNum;

    @ApiModelProperty(value = "创客姓名")
    private String makerName;

    @ApiModelProperty(value = "创客身份证号码")
    private String makerIdcardNo;

    @ApiModelProperty(value = "创客手机号")
    private String makerPhoneNumber;

    @ApiModelProperty(value = "非创客姓名")
    private String invoicePeopleName;

    @ApiModelProperty(value = "非创客身份证号码")
    private String invoicePeopleIdcardNo;

    @ApiModelProperty(value = "非创客手机号")
    private String invoicePeoplePhoneNumber;

}
