package com.lgyun.system.order.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 分包-门征
 * @author jun.
 * @date 2020/8/14.
 * @time 14:31.
 */
@Data
@ApiModel(value = "EnterpriseSubcontractInvoiceVO对象",description = "EnterpriseSubcontractInvoiceVO对象")
public class EnterpriseSubcontractPortalVO {
    private static final long serialVersionUID = 1L;
    private Long payEnterpriseId;
    private Long payMakerId;
    private Long serviceProviderId;
    private Long makerInvoiceId;
    private Long makerTaxRecordId;
    private String voiceSerialNo;
    private String makerVoiceUrl;
    private String makerTaxUrl;
    private String companyInvoiceState;
    private String serviceProviderName;
    private String invoicePrintDate;
}
