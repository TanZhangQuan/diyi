package com.lgyun.system.order.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/9/18.
 * @time 16:16.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceAdminDetailVO对象", description = "SelfHelpInvoiceAdminDetailVO对象")
public class SelfHelpInvoiceAdminDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *自主开票主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;
    /**
     *商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     *自主开票费用id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceFeeId;

    /**
     *商户名称
     */
    private String enterpriseName;
    /**
     * 纳税号
     */
    private String invoiceTaxNo;
    /**
     *开票地址
     */
    private String invoiceAddress;
    /**
     *开票电话
     */
    private String invoiceTelNo;
    /**
     *发票银行卡
     */
    private String invoiceBankName;
    /**
     *发票银行卡号
     */
    private String invoiceAccount;
    /**
     *清单url
     */
    private String listFile;
    /**
     *众包支付模式：标准支付；扩展支付；商户代付税费
     */
    private String payType;
    /**
     *价税合计额
     */
    private String totalChargeMoneyNum;
    /**
     *总需支付服务商税费
     */
    private String totalPayProviderFee;
    /**
     *总服务税费
     */
    private String serviceAndTaxMoney;
    /**
     *总服务外包费：合计，明细价税合计，依据明细自动计算
     */
    private String serviceFee;
    /**
     *服务税费率
     */
    private String serviceRate;
    /**
     *总税费，一般不填
     */
    private String serviceTax;
    /**
     *总开票手续费
     */
    private String serviceInvoiceFee;
    /**
     *总身份验证费
     */
    private String idendityConfirmFee;
    /**
     *状态
     */
    private String currentState;
    /**
     *创建时间
     */
    private String createTime;
    /**
     *收件人姓名
     */
    private String addressName;
    /**
     *收件人电话
     */
    private String addressPhone;
    /**
     *区
     */
    private String area;
    /**
     *城市
     */
    private String city;
    /**
     *详细地址
     */
    private String detailedAddress;
    /**
     *省
     */
    private String province;
    /**
     *户名
     */
    private String accountName;
    /**
     *账号
     */
    private String accountNo;
    /**
     *开户行
     */
    private String accountBank;
    /**
     *基本存款账号
     */
    private String basicAccountBank;
}
