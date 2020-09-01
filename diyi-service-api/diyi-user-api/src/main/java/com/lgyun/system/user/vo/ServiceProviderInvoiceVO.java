package com.lgyun.system.user.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/7/28.
 * @time 14:55.
 */
@Data
@ApiModel(value = "ServiceProviderInvoiceVO对象", description = "ServiceProviderInvoiceVO对象")
public class ServiceProviderInvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票资料-公司名称
     */
    private String invoiceEnterpriseName;

    /**
     * 开票资料-税号
     */
    private String invoiceTaxNo;

    /**
     * 开票资料-地址
     */
    private String invoiceAddress;

    /**
     * 开票资料-电话
     */
    private String invoiceTelNo;

    /**
     * 开票资料-开户银行
     */
    private String invoiceBankName;

    /**
     * 开票资料-账户名
     */
    private String invoiceAccountName;

    /**
     * 开票资料-账号
     */
    private String invoiceAccount;

}
