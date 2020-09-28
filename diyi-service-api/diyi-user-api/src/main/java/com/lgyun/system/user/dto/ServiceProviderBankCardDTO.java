package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author tzq
 * @Description
 * @return
 * @date 2020.06.27
 */
@Data
public class ServiceProviderBankCardDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票资料-账号
     */
    @NotBlank(message = "请输入银行账号")
    private String invoiceAccount;

    /**
     * 开票资料-开户银行
     */
    @NotBlank(message = "请输入开户银行")
    private String invoiceBankName;

    /**
     * 开票资料-账户名
     */
    @NotBlank(message = "请输入账户名")
    private String invoiceAccountName;

}
