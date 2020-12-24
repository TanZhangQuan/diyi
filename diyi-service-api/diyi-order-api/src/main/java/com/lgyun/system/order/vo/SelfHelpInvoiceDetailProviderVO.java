package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceDetailProviderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客或非创客开票人ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "创客或非创客开票人名称")
    private String name;

    @ApiModelProperty(value = "创客或非创客开票人身份证号码")
    private String idcardNo;

    @ApiModelProperty(value = "创客或非创客开票人手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "创客银行卡号")
    private String bankNo;

}
