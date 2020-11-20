package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AccountType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 服务商收款账户信息表 Entity
 *
 * @author tzq
 * @since 2020-11-08 20:57:37
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_service_provider_account")
public class ServiceProviderAccountEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

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
     * 基本存款账号，对公账号才需要
     */
    private String basicAccountBank;

    /**
     * 个人创客到手业务外包费账户
     */
    private String makerSafeAccount;

    /**
     * 服务税费账户
     */
    private String providerTaxAndFeeAccount;

    /**
     * 是否默认
     */
    private Boolean isDefault;

}
