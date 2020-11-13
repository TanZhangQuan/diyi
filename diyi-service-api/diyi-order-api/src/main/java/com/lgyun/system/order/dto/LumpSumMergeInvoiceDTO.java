package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.InvoiceMode;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

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

    /**
     *货物或应税劳务、服务名称,发票类
     */
    @NotBlank(message = "请输入发票分类")
    private String invoiceCategory;

    /**
     * 开票方式
     */
    @NotNull(message = "请选择开票方式")
    private InvoiceMode invoiceMode;

    /**
     * 部分开票金额
     */
    private BigDecimal partInvoiceAmount;
}
