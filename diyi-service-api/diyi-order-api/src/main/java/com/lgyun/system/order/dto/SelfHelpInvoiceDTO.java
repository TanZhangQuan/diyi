package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import com.lgyun.common.enumeration.ObjectType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2020/7/10.
 * @time 16:35.
 */
@Data
public class SelfHelpInvoiceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 购买方
     */
    @NotNull(message = "购买方Id不能为空")
    private Long enterpriseId;

    /**
     * 开票人身份
     */
    @NotBlank(message = "请选择开票人身份")
    private MakerType makerType;

    /**
     * 开票类目
     */
    @NotBlank(message = "开票类目不能为空")
    private String invoiceType;

    /**
     * 开票清单文件
     */
    @NotBlank(message = "开票清单文件不能为空")
    private String listFile;

    /**
     * 价税合计额
     */
    @NotNull(message = "价税合计额不能为空")
    private BigDecimal chargeMoneyNum;

    /**
     * 收件地址
     */
    @NotNull(message = "收件地址不能为空")
    private Long addressId;

    /**
     * 流水凭证
     */
    @NotBlank(message = "流水凭证不能为空")
    private String flowContractUrl;

    /**
     * 业务合同
     */
    @NotBlank(message = "业务合同不能为空")
    private String businessContractUrl;

    /**
     * 交付支付验收单URL
     */
    @NotBlank(message = "交付支付验收单URL不能为空")
    private String deliverSheetUrl;

    /**
     * 账户余额url
     */
    @NotBlank(message = "账户余额url不能为空")
    private String accountBalanceUrl;

    /**
     * 申请人Id
     */
    @NotNull(message = "申请人创客的Id不能为空")
    private Long objectId;

    /**
     * 申请人的身份
     */
    @NotNull(message = "申请人的身份不能为空")
    private ObjectType objectType;
}
