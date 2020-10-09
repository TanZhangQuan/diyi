package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/9/16.
 * @time 15:51.
 */
@Data
public class SummaryInvoiceDTO implements Serializable {

    /**
     * 商户支付清单Id
     */
    @NotNull(message = "请输入商户支付清单Id")
    private Long payEnterpriseId;

    /**
     * 服务商名字
     */
    @NotBlank(message = "请输入服务商名字")
    private String serviceProviderName;

    /**
     * 发票代码
     */
    @NotBlank(message = "请输入发票代码")
    private String invoiceTypeNo;

    /**
     * 发票号码
     */
    @NotBlank(message = "请输入发票号码")
    private String invoiceSerialNo;

    /**
     * 货物或应税劳务、服务名称
     */
    @NotBlank(message = "请输入货物或应税劳务、服务名称")
    private String invoiceCategory;

    /**
     * 汇总代开发票URL
     */
    @NotBlank(message = "请输入汇总代开发票URL")
    private String companyInvoiceUrl;

    /**
     * 总完税证明URL
     */
    @NotBlank(message = "请输入总完税证明URL")
    private String makerTaxUrl;

    /**
     * 清单式完税凭证URL
     */
    @NotBlank(message = "请输入清单式完税凭证URL")
    private String makerTaxListUrl;

}
