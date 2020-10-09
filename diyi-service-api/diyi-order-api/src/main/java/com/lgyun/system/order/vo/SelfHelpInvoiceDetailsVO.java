package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/13.
 * @time 15:29.
 */
@Data
public class SelfHelpInvoiceDetailsVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 地址ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long addressId;

    /**
     * 开票人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePeopleId;

    /**
     * 自助开票详情ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceDetailId;

    /**
     * 自助开票收费ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long handPayId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 税号
     */
    private String taxNo;

    /**
     * 员工姓名
     */
    private String employeeName;

    /**
     * 手机号
     */
    private String phoneNo;

    /**
     * 银行名称
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 流水凭证
     */
    private String flowContractUrl;

    /**
     * 业务合同URL(多张)
     */
    private String businessContractUrl;

    /**
     * 收件人
     */
    private String addressName;

    /**
     * 收件人电话
     */
    private String addressPhone;

    /**
     * 地区
     */
    private String area;

    /**
     * 城市
     */
    private String city;

    /**
     * 省
     */
    private String province;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 身份证姓名
     */
    private String idCardName;

    /**
     * 身份证号码
     */
    private String idCardNo;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 身份证正面图
     */
    private String idCardPic;

    /**
     * 身份证反面图
     */
    private String idCardPicBack;

    /**
     * 核价日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date givePriceDate;

    /**
     * 服务税费总额
     */
    private BigDecimal totalTaxFee;

    /**
     * 基础税费
     */
    private BigDecimal basicTaxFee;

    /**
     * 基础税费率
     */
    private BigDecimal basicTaxFeeRate;

    /**
     * 开票手续费
     */
    private BigDecimal invoiceFee;

    /**
     * 身份验证费总额
     */
    private BigDecimal identifyFee;

    /**
     * 交付支付验收单URL
     */
    private String deliverSheetUrl;
}
