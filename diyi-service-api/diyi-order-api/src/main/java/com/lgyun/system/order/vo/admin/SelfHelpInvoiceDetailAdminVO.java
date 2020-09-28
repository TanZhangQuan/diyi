package com.lgyun.system.order.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePrintState;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/9/22.
 * @time 10:57.
 */
@Data
public class SelfHelpInvoiceDetailAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *自助开票主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    /**
     *自助开票明细表Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceDetailId;

    /**
     *自助开票服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyProviderId;

    /**
     *自助开票服务商明细id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyProviderDetailId;

    /**
     *自助开票快递id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceExpressId;

    /**
     *开票人
     */
    private String invoicePeopleName;

    /**
     *开票人身份证
     */
    private String idCardNo;

    /**
     *开票人电话
     */
    private String phoneNumber;

    /**
     *价税合计额
     */
    private String chargeMoneyNum;

    /**
     *服务税率
     */
    private String serviceRate;

    /**
     *支付回单
     */
    private String flowContractUrl;

    /**
     *业务合同URL
     */
    private String businessContractUrl;

    /**
     *交付支付验收单URL
     */
    private String deliverSheetUrl;

    /**
     *发票，可能多张
     */
    private String invoiceScanPictures;

    /**
     *税票
     */
    private String  taxScanPictures;

    /**
     * 快递公司
     */
    private String expressCompanyName;

    /**
     * 快递单号
     */
    private String expressNo;

    /**
     * 开票状态
     */
    private InvoicePrintState invoicePrintState;

}
