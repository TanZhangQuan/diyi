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
    @NotNull(message = "请选择购买方")
    private Long enterpriseId;

    /**
     * 开票人身份
     */
    @NotBlank(message = "请选择开票人身份")
    private MakerType makerType;

    /**
     * 开票类目
     */
    @NotBlank(message = "请选择开票类目")
    private String invoiceType;

    /**
     * 开票清单文件
     */
    @NotBlank(message = "请上传开票清单文件")
    private String listFile;

    /**
     * 价税合计额
     */
    @NotNull(message = "请输入价税合计额")
    private BigDecimal chargeMoneyNum;

    /**
     * 收件地址
     */
    @NotNull(message = "请输入收件地址")
    private Long addressId;

    /**
     * 流水凭证
     */
    @NotBlank(message = "请上传流水凭证")
    private String flowContractUrl;

    /**
     * 业务合同
     */
    @NotBlank(message = "请上传业务合同")
    private String businessContractUrl;

    /**
     * 账户余额url
     */
    @NotBlank(message = "请上传账户余额")
    private String accountBalanceUrl;

    /**
     * 申请人Id
     */
    @NotNull(message = "请选择申请人创客")
    private Long objectId;

    /**
     * 申请人的身份
     */
    @NotNull(message = "请选择申请人的身份")
    private ObjectType objectType;
}
