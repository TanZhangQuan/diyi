package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.MakerType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author jun.
 * @date 2020/12/9.
 * @time 18:37.
 */
@Data
public class NaturalPersonConfirmSubmitDto implements Serializable {

    /**
     * 商户名称
     */
    @NotBlank(message = "请输入商户名称")
    private String enterpriseName;
    /**
     * 众包支付模型
     */
    @NotBlank(message = "请输入众包支付模型")
    private CrowdSourcingPayType payType;
    /**
     * 开票类目
     */
    @NotBlank(message = "请输入开票类目")
    private String invoiceType;
    /**
     * 地址id
     */
    @NotNull(message = "请输入地址")
    private Long addressId;

    private Long serviceProviderId;

    private String invoiceCategory;

    private MakerType makerType;
    /**
     * 清单
     */
    @NotBlank(message = "请输入清单")
    private String listFile;

    /**
     * 自助开票清单明细
     */
    private List<InvoiceListExcelDto> list;

}
