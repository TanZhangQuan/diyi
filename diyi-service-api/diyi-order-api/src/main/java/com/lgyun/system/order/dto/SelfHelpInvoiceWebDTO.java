package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.InvoicePeopleType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2020/7/10.
 * @time 16:35.
 */
@Data
public class SelfHelpInvoiceWebDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 购买方
     */
    private Long enterpriseId;

    /**
     * 开票人
     */
    private Long selfHelpInvoicePersonId;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 收件地址
     */
    private Long addressId;

    /**
     * 流水凭证
     */
    private String flowContractUrl;

    /**
     * 业务合同
     */
    private String businessContractUrl;

    /**
     * 开票人的身份
     */
    private InvoicePeopleType invoicePeopleType;

    /**
     * 申请商户ID
     */
    private Long applyEnterpriseId;

    /**
     * 账户余额url
     */
    private String accountBalanceUrl;

    /**
     * 开票清单
     */
    private String listFile;

}
