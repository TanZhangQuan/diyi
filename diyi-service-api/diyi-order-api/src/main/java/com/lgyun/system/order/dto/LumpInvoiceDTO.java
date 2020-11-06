package com.lgyun.system.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author .
 * @date 2020/11/6.
 * @time 14:43.
 */
@Data
public class LumpInvoiceDTO {

    /**
     *总包id
     */
    @NotNull(message = "总包id不能为空")
    private Long invoicePrintId;

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
     * 发票代码
     */
    @NotBlank(message = "请输入发票代码")
    private String invoiceTypeNo;

    /**
     *发票号码
     */
    @NotBlank(message = "请输入发票号码")
    private String invoiceSerialNo;

    /**
     *货物或应税劳务、服务名称,发票类
     */
    @NotBlank(message = "请输入发票分类")
    private String invoiceCategory;
}
