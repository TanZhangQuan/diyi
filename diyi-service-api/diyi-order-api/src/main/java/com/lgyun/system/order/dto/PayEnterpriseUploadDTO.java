package com.lgyun.system.order.dto;

import com.lgyun.common.enumeration.MakerType;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 上传支付清单
 *
 * @author tzq.
 * @date 2020/7/8.
 * @time 16:27.
 */
@Data
public class PayEnterpriseUploadDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    @NotNull(message = "请选择服务商")
    private Long serviceProviderId;

    /**
     * 创客类型
     */
    @NotNull(message = "请选择创客类型")
    private MakerType makerType;

    /**
     * 支付清单URL
     */
    @NotBlank(message = "请上传支付清单")
    private String chargeListUrl;

    /**
     * 工单ID
     */
    private Long worksheetId;

    /**
     * 企业总支付额价税合计总额=服务外包费总额+身份验证费总额/个体户年费总额+第三方支付手续费总额
     */
    @NotNull(message = "请输入企业总支付额价税合计总额")
    @Min(value = 0, message = "企业总支付额价税合计总额不能小于0")
    @Max(value = 999999999, message = "企业总支付额价税合计总额不能大于999999999")
    private BigDecimal payToPlatformAmount;

    /**
     * 服务税费总额=服务外包费总额*服务税费率
     */
    @NotNull(message = "请输入服务税费总额")
    @Min(value = 0, message = "服务税费总额不能小于0")
    @Max(value = 999999999, message = "服务税费总额不能大于999999999")
    private BigDecimal totalTaxFee;

    /**
     * 创客到手总额
     */
    @NotNull(message = "请输入创客到手总额")
    @Min(value = 0, message = "创客到手总额不能小于0")
    @Max(value = 999999999, message = "创客到手总额不能大于999999999")
    private BigDecimal totalMakerNetIncome;

    /**
     * 服务税费率
     */
    @NotNull(message = "请输入服务税费率")
    @Min(value = 0, message = "服务税费率不能小于0")
    @Max(value = 100, message = "服务税费率不能大于100")
    private BigDecimal serviceRate;

    /**
     * 服务外包费总额
     */
    @NotNull(message = "请输入服务外包费总额")
    @Min(value = 0, message = "服务外包费总额不能小于0")
    @Max(value = 999999999, message = "服务外包费总额不能大于999999999")
    private BigDecimal sourcingAmount;

    /**
     * 企业年费总额，个体户，个独，有限公司都有年费，自然人没有年费
     */
    @NotNull(message = "请输入企业年费总额")
    @Min(value = 0, message = "企业年费总额不能小于0")
    @Max(value = 999999999, message = "企业年费总额不能大于999999999")
    private BigDecimal enterpriseBusinessAnnualFee;

    /**
     * 身份验证费总额
     */
    @NotNull(message = "请输入身份验证费总额")
    @Min(value = 0, message = "身份验证费总额不能小于0")
    @Max(value = 999999999, message = "身份验证费总额不能大于999999999")
    private BigDecimal identifyFee;

    /**
     * 第三方支付手续费总额
     */
    @NotNull(message = "请输入第三方支付手续费总额")
    @Min(value = 0, message = "第三方支付手续费总额不能小于0")
    @Max(value = 999999999, message = "第三方支付手续费总额不能大于999999999")
    private BigDecimal serviceFee;

    /**
     * 创客数
     */
    @NotNull(message = "请输入创客数")
    @Min(value = 1, message = "创客数不能小于1")
    @Max(value = 999999999, message = "创客数不能大于999999999")
    private Integer makerNum;

    /**
     * 支付说明
     */
    private String payMemo;

    /**
     * 支付回单(多张)
     */
    @NotBlank(message = "请上传支付回单")
    private String payReceiptUrls;

}
