package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "上传支付清单DTO")
public class PayEnterpriseCreateOrUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总包支付清单ID")
    private Long payenterpriseId;

    @ApiModelProperty(value = "服务商ID")
    @NotNull(message = "请选择服务商")
    private Long serviceProviderId;

    @ApiModelProperty(value = "创客类型", notes = "com.lgyun.common.enumeration.MakerType")
    @NotNull(message = "请选择创客类型")
    private MakerType makerType;

    @ApiModelProperty(value = "总包支付清单")
    @NotBlank(message = "请上传总包支付清单")
    private String chargeListUrl;

    @ApiModelProperty(value = "工单ID")
    private Long worksheetId;

    @ApiModelProperty(value = "支付说明")
    private String payMemo;

    @ApiModelProperty(value = "支付回单(多张)")
    @NotBlank(message = "请上传支付回单")
    private String enterprisePayReceiptUrls;

}
