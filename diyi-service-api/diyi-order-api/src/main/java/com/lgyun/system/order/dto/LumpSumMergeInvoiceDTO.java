package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author .
 * @date 2020/9/16.
 * @time 16:07.
 */
@Data
public class LumpSumMergeInvoiceDTO implements Serializable {

    /**
     * 商户支付清单Id
     */
    @NotBlank(message = "支付清单id不能为空")
    private String payEnterpriseIds;

    /**
     *服务商名字
     */
    @NotBlank(message = "请输入服务商名字")
    private String serviceProviderName;

    /**
     *总包申请id
     */
    private Long applicationId;

    /**
     *发票url
     */
    @NotBlank(message = "请输入发票url")
    private String companyInvoiceUrl;

    /**
     *快递单号
     */
    @NotBlank(message = "请输入快递单号")
    private String expressSheetNo;

    /**
     *快递公司
     */
    @NotBlank(message = "请输入快递公司")
    private String expressCompanyName;

    /**
     *说明
     */
    private String invoiceDesc;

    private Long serviceProviderId;
}
