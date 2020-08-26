package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.PayEnterpriseAuditState;
import io.swagger.annotations.ApiModel;
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
@ApiModel(value = "PayListVO对象", description = "PayListVO对象")
public class PayEnterpriseMakersListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //总包或分包支付清单编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //总包支付清单编号(分包时赋值)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    //商户名称
    private String enterpriseName;

    //服务商名称
    private String serviceProviderName;

    //总包支付清单URL
    private String chargeListUrl;

    //总包支付回单图片URL地址(多张逗号隔开)
    private String enterprisePayReceiptUrls;

    //工单编号
    private String worksheetNo;

    //交付支付验收单URL(多张逗号隔开)
    private String acceptPaysheetUrls;

    //分包支付回单图片URL地址(多张逗号隔开)
    private String makerPayReceiptUrls;

    //审核状态
    private PayEnterpriseAuditState auditState;

    //创建时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    //支付总额
    private BigDecimal payToPlatformAmount;

}
