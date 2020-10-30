package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lgyun.common.enumeration.MakerType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
public class PayEnterpriseUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户支付清单ID
     */
    private Long id;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 创客类型
     */
    private MakerType makerType;

    /**
     * 支付清单URL
     */
    private String chargeListUrl;

    /**
     * 工单ID
     */
    private Long worksheetId;

    /**
     * 工单编号
     */
    private String worksheetNo;

    /**
     * 工单名称
     */
    private String worksheetName;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date worksheetCreateTime;

    /**
     * 企业总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额
     */
    private BigDecimal payToPlatformAmount;

    /**
     * 服务税费总额=服务外包费总额*服务税费率
     */
    private BigDecimal totalTaxFee;

    /**
     * 创客到手总额
     */
    private BigDecimal totalMakerNetIncome;

    /**
     * 服务税费率
     */
    private BigDecimal serviceRate;

    /**
     * 服务外包费总额
     */
    private BigDecimal sourcingAmount;

    /**
     * 企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
     */
    private BigDecimal enterpriseBusinessAnnualFee;

    /**
     * 身份验证费总额
     */
    private BigDecimal identifyFee;

    /**
     * 第三方支付手续费总额
     */
    private BigDecimal serviceFee;

    /**
     * 创客数
     */
    private Integer makerNum;

    /**
     * 支付说明
     */
    private String payMemo;

    /**
     * 支付回单(多张)
     */
    private String enterprisePayReceiptUrls;

}
