package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
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
public class NaturalPersonConfirmSubmitDTO implements Serializable {


    private ObjectType objectType;

    private Long objectId;
    /**
     * 众包支付模型
     */
    @NotNull(message = "请输入众包支付模型")
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

    /**
     * 开票人身份类别
     */
    @NotNull(message = "请输入开票人身份类别")
    private MakerType makerType;
    /**
     * 清单
     */
    @NotBlank(message = "请输入清单")
    private String listFile;

    /**
     * 自助开票清单明细
     */
    private List<InvoiceListExcelDTO> list;

}
