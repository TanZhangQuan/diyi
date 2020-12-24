package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@ApiModel(description = "XXXXX")
public class SelfHelpInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "购买方")
    @NotNull(message = "请选择购买方")
    private Long enterpriseId;

    @ApiModelProperty(value = "开票人身份", notes = "com.lgyun.common.enumeration.MakerType")
    @NotBlank(message = "请选择开票人身份")
    private MakerType makerType;

    @ApiModelProperty(value = "开票类目")
    @NotBlank(message = "请选择开票类目")
    private String invoiceType;

    @ApiModelProperty(value = "开票清单文件")
    @NotBlank(message = "请上传开票清单文件")
    private String listFile;

    @ApiModelProperty(value = "价税合计额")
    @NotNull(message = "请输入价税合计额")
    private BigDecimal chargeMoneyNum;

    @ApiModelProperty(value = "收件地址")
    @NotNull(message = "请输入收件地址")
    private Long addressId;

    @ApiModelProperty(value = "流水凭证")
    @NotBlank(message = "请上传流水凭证")
    private String flowContractUrl;

    @ApiModelProperty(value = "业务合同")
    @NotBlank(message = "请上传业务合同")
    private String businessContractUrl;

    @ApiModelProperty(value = "账户余额")
    @NotBlank(message = "请上传账户余额")
    private String accountBalanceUrl;

    @ApiModelProperty(value = "申请人")
    @NotNull(message = "请选择申请人")
    private Long objectId;

    @ApiModelProperty(value = "申请人的身份", notes = "com.lgyun.common.enumeration.ObjectType")
    @NotNull(message = "请选择申请人的身份")
    private ObjectType objectType;
}
