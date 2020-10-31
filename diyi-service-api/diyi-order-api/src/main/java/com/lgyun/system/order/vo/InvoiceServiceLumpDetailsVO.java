package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务商查询总包发票详情
 * @author .
 * @date 2020/9/5.
 * @time 14:26.
 */
@Data
public class InvoiceServiceLumpDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 地址id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addressId;

    /**
     * 总包发票id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePrintId;

    /**
     * 总包发票详细表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceDetailId;

    /**
     * 价税合计额
     */
    private String payToPlatformAmount;

    /**
     * 开票分类
     */
    private String invoiceCatalogName;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 纳税号码
     */
    private String invoiceTaxNo;

    /**
     * 开票资料-地址和电话
     */
    private String invoiceAddressPhone;

    /**
     * 开票资料-开户银行和账号
     */
    private String invoiceBankNameAccount;

    /**
     * 开票资料-公司名称
     */
    private String invoiceEnterpriseName;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 支付清单url
     */
    private String chargeListUrl;

    /**
     * 工单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String worksheetId;

    /**
     * 开票状态
     */
    private InvoiceState companyInvoiceState;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 收件人姓名
     */
    private String addressName;

    /**
     * 收件人电话
     */
    private String addressPhone;

    /**
     * 地区
     */
    private String area;

    /**
     * 城市
     */
    private String city;

    /**
     * 省份
     */
    private String province;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 快递单号
     */
    private String expressSheetNo;

    /**
     * 快递公司名字
     */
    private String expressCompanyName;

    /**
     * 发票url
     */
    private String companyInvoiceUrl;

    /**
     * 支付回单
     */
    private String enterprisePayReceiptUrl;
}
