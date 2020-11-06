package com.lgyun.system.user.vo;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商户的开票信息
 */
@Data
public class EnterpriseInvoiceVO {
    /**
     * 公司名称
     */
    @NotBlank(message = "请输入公司名称")
    private String invoiceEnterpriseName;

    /**
     * 纳税识别号
     */
    @NotBlank(message = "请输入纳税识别号")
    private String invoiceTaxNo;

    /**
     * 开票资料-地址和电话
     */
    @NotBlank(message = "请输入开票地址和电话")
    private String invoiceAddressPhone;

    /**
     * 开票资料-开户银行和账号
     */
    @NotBlank(message = "请输入开票开户银行和账号")
    private String invoiceBankNameAccount;

}
