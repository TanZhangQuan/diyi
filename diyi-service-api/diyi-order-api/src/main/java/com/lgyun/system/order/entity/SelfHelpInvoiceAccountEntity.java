package com.lgyun.system.order.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_self_help_invoice_account")
public class SelfHelpInvoiceAccountEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

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
     * 基本存款账号
     */
    private String basicAccountBank;

    /**
     * 是否默认
     */
    private Boolean boolDefault;

}
