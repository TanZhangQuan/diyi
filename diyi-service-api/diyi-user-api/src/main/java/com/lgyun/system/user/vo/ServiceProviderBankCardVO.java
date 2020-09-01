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
@ApiModel(value = "ServiceProviderBankCardVO对象", description = "ServiceProviderBankCardVO对象")
public class ServiceProviderBankCardVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 开票资料-账号
     */
    private String invoiceAccount;

    /**
     * 开票资料-开户银行
     */
    private String invoiceBankName;

    /**
     * 开票资料-账户名
     */
    private String invoiceAccountName;

}
