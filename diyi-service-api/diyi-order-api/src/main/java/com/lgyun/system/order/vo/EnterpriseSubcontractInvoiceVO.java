package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 分包 -汇总
 * @author .
 * @date 2020/8/14.
 * @time 9:47.
 */
@Data
public class EnterpriseSubcontractInvoiceVO {
    private static final long serialVersionUID = 1L;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerTotalInvoiceId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    private String invoiceSerialNo;

    private String companyInvoiceUrl;

    private String makerTaxUrl;

    private String companyInvoiceState;

    private String serviceProviderName;

    private String invoicePrintDate;
}
