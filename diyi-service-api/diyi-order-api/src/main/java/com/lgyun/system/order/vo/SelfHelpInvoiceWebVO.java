package com.lgyun.system.order.vo;

import com.lgyun.common.enumeration.InvoiceState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/8/3.
 * @time 15:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceVO对象", description = "SelfHelpInvoiceWebVO对象")
public class SelfHelpInvoiceWebVO implements Serializable {

    /**
     * 自助开票id
     */
    private Long selfHelpInvoiceId;

    /**
     * 购买方Id
     */
    private Long enterpriseId;

    /**
     * 自助开票详情id
     */
    private Long selfHelpInvoiceDetailId;

    /**
     * 地址Id
     */
    private Long addressId;

    /**
     * 自助人id
     */
    private Long invoicePersonId;

    /**
     * 自助费用id
     */
    private Long selfHelpInvoiceFeeId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 价税合计额
     */
    private String chargeMoneyNum;

    /**
     * 开票人名字
     */
    private String invoicePeopleName;

    /**
     * 身份证正面
     */
    private String idCardPic;

    /**
     * 身份证反面
     */
    private String idCardPicBack;

    /**
     * 流水回单URL
     */
    private String flowContractUrl;

    /**
     * 业务合同URL
     */
    private String businessContractUrl;

    /**
     * 开票状态 1 未开 2已开
     */
    private InvoiceState invoiceState;

    /**
     * 开票时间
     */
    private Date invoiceDate;
}
