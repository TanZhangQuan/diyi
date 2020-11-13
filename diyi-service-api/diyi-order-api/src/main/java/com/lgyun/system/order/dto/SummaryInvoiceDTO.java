package com.lgyun.system.order.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/10/27.
 * @time 14:31.
 */
@Data
public class SummaryInvoiceDTO implements Serializable {

    /**
     * 商户清单id，多个用逗号隔开
     */
    @NotBlank(message = "请输入商户清单id")
    private String payEnterpriseIds;
    /**
     *发票代码
     */
    @NotBlank(message = "请输入发票代码")
    private String invoiceTypeNo;
    /**
     *发票号码
     */
    @NotBlank(message = "请输入发票号码")
    private String invoiceSerialNo;
    /**
     * 开票日期
     */
    @NotNull(message = "请输入开票日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invoiceDatetime;

    /**
     *货物或应税劳务、服务名称
     */
    @NotBlank(message = "请输入货物或应税劳务、服务名称")
    private String invoiceCategory;
    /**
     *发票url
     */
    @NotNull(message = "请输入发票url")
    private String companyInvoiceUrl;
    /**
     * 总完税证明URL
     */
    @NotNull(message = "请输入总完税证明URL")
    private String makerTaxUrl;

    /**
     * 清单式完税凭证URL
     */
    private String makerTaxListUrl;

    /**
     *销售方
     */
    private String saleCompany;
}
