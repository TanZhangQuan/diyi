package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/7/28.
 * @time 14:55.
 */
@Data
public class InvoiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户/服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 开票资料-公司名称
     */
    private String invoiceEnterpriseName;

    /**
     * 开票资料-税号
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

}