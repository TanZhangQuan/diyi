package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountType;
import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceProviderAccountListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商收款账户信息ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 账户类型：银行账户；第三方支付账户；个人账户；其他
     */
    private AccountType accountType;

    /**
     * 账户名称
     */
    private String accountName;

    /**
     * 银行账号
     */
    private String accountNo;

    /**
     * 开户银行
     */
    private String accountBank;

    /**
     * 是否默认
     */
    private Boolean isDefault;

}
