package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
public class PayEnterpriseListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户支付清单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 总包支付清单URL
     */
    private String chargeListUrl;

    /**
     * 总包支付回单图片(多张逗号隔开)
     */
    private String enterprisePayReceiptUrls;

    /**
     * 工单ID
     */
    private String worksheetId;

    /**
     * 总包+分包交付支付验收单
     */
    private String acceptPaysheetUrls;

    /**
     * 审核状态
     */
    private PayEnterpriseAuditState auditState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
