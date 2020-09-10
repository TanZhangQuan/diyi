package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/8/3.
 * @time 15:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceSingleByEnterpriseVO对象", description = "SelfHelpInvoiceSingleByEnterpriseVO对象")
public class SelfHelpInvoiceSingleByEnterpriseVO implements Serializable {

    /**
     * 自助开票id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 开票清单文件
     */
    private String listFile;

    /**
     * 众包支付模式：标准支付；扩展支付；商户代付税费
     */
    private CrowdSourcingPayType payType;

    /**
     * 开票类目
     */
    private String invoiceTypes;

    /**
     * 总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算
     */
    private BigDecimal totalPayProviderFee;

    /**
     * 业务合同URL(多张)
     */
    private String businessContractUrls;

    /**
     * 支付回单URL(多张)
     */
    private String flowContractUrls;

    /**
     * 验收单URL(多张)
     */
    private String acceptPaysheetUrls;

    /**
     * 当前状态：未申请，申请编辑中，审核中；已通过开票中；已驳回；已开票结束
     */
    private SelfHelpInvoiceApplyState currentState;

    /**
     * 开票时间
     */
    private Date createTime;

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
     * 开票资料-账号
     */
    private String invoiceAccount;

    /**
     * 收件人
     */
    private String addressName;

    /**
     * 手机号码
     */
    private String addressPhone;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 总服务税费=总服务外包费*服务税费率
     */
    private BigDecimal serviceAndTaxMoney;

    /**
     * 总服务外包费：合计，明细价税合计，依据明细自动计算
     */
    private BigDecimal serviceFee;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 总税费，一般不填
     */
    private BigDecimal serviceTax;

    /**
     * 总开票手续费
     */
    private BigDecimal serviceInvoiceFee;

    /**
     * 总身份验证费
     */
    private BigDecimal idendityConfirmFee;

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

}