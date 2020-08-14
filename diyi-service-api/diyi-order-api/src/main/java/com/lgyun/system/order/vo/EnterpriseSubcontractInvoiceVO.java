package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 分包 -汇总
 * @author .
 * @date 2020/8/14.
 * @time 9:47.
 */
@Data
@ApiModel(value = "EnterpriseSubcontractInvoiceVO对象",description = "EnterpriseSubcontractInvoiceVO对象")
public class EnterpriseSubcontractInvoiceVO {
    private static final long serialVersionUID = 1L;
    private Long payEnterpriseId;
    private Long makerTotalInvoiceId;
    private Long serviceProviderId;
    private String invoiceSerialNo;
    private String companyInvoiceUrl;
    private String makerTaxUrl;
    private String companyInvoiceState;
    private String serviceProviderName;
    private String invoicePrintDate;
}
