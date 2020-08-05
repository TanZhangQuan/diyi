package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2020/7/10.
 * @time 16:35.
 */
@Data
public class SelfHelpInvoiceWebDto implements Serializable {
    private static final long serialVersionUID = 1L;

    //购买方
   private Long companyId;

   //开票人
   private Long selfHelpInvoicePersonId;

   //开票类目
   private String invoiceType;

   //价税合计额
    private BigDecimal chargeMoneyNum;

    //收件地址
    private Long addressId;

    //流水凭证
    private  String flowContractUrl;

    //业务合同
    private String businessContractUrl;

    //开票人的身份
    private MakerType invoicePeopleType;

    //申请商户id
    private Long applyEnterpriseId;

    //服务税费率
    private BigDecimal serviceRate;

    //总服务税费
    private BigDecimal serviceAndTaxMoney;

    //总服务费
    private BigDecimal serviceFee;

    //总税
    private BigDecimal serviceTax;

    //总开票手续费
    private BigDecimal serviceInvoiceFee;

    //总身份验证费
    private BigDecimal idendityConfirmFee;

    private Long businessEnterpriseId;

    private String accountBalanceUrl;

    private String listFile;
}
