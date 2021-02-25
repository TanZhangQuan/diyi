package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.InvoiceCategory;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "XXXXX")
public class NaturalPersonConfirmSubmitDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "对象类型", notes = "com.lgyun.common.enumeration.ObjectType")
    private ObjectType objectType;

    @ApiModelProperty(value = "对象ID")
    private Long objectId;

    @ApiModelProperty(value = "众包支付模型", notes = "com.lgyun.common.enumeration.CrowdSourcingPayType")
    @NotNull(message = "请输入众包支付模型")
    private CrowdSourcingPayType payType;

    @ApiModelProperty(value = "开票类目")
    @NotBlank(message = "请输入开票类目")
    private String invoiceType;

    @ApiModelProperty(value = "地址ID")
    @NotNull(message = "请输入地址")
    private Long addressId;

    @ApiModelProperty(value = "服务商ID")
    private Long serviceProviderId;

    @ApiModelProperty(value = "发票分类", notes = "com.lgyun.common.enumeration.InvoiceCategory")
    private InvoiceCategory invoiceCategory;

    @ApiModelProperty(value = "开票人身份类别", notes = "com.lgyun.common.enumeration.MakerType")
    @NotNull(message = "请输入开票人身份类别")
    private MakerType makerType;

    @ApiModelProperty(value = "支付清单")
    @NotBlank(message = "请输入清单")
    private String listFile;

    @ApiModelProperty(value = "自助开票清单明细")
    private List<InvoiceListExcelDTO> list;

}
