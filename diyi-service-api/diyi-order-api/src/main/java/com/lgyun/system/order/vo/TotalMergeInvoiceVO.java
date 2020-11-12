package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/11/12.
 * @time 15:51.
 */
@Data
public class TotalMergeInvoiceVO implements Serializable {
    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;
    /**
     * 商户名称
     */
    private String enterpriseName;
    /**
     * 支付清单url
     */
    private String chargeListUrl;
    /**
     * 支付回单url,过个逗号个开
     */
    private String enterprisePayReceiptUrl;
    /**
     * 工单Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String worksheetId;
    /**
     * 支付交付验收单url,多个逗号隔开
     */
    private String acceptPaysheetUrl;
    /**
     * 价税合计额
     */
    private String payToPlatformAmount;
    /**
     * 服务商
     */
    private String serviceProviderName;
    /**
     * 开票状态
     */
    private CompanyInvoiceState companyInvoiceState;
    /**
     * 时间
     */
    private String createTime;
    /**
     * 开票信息名称
     */
    private String invoiceEnterpriseName;
    /**
     * 开票信息纳税人识别号
     */
    private String invoiceTaxNo;
    /**
     * 开票信息电话是地址
     */
    private String invoiceAddressPhone;
    /**
     * 开票信息银行卡号
     */
    private String invoiceBankNameAccount;
    /**
     * 收件人姓名
     */
    private String addressName;
    /**
     * 收件人电话
     */
    private String addressPhone;
    /**
     * 收件人地区
     */
    private String area;
    /**
     * 收件人城市
     */
    private String city;
    /**
     * 收件人省份
     */
    private String province;
    /**
     * 收件人收人详细地址
     */
    private String detailedAddress;
}
