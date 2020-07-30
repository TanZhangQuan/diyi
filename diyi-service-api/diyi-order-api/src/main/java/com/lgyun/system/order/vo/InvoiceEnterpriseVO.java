package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author .
 * @date 2020/7/22.
 * @time 16:30.
 */
@Data
@ApiModel(value = "InvoiceEnterpriseVO对象", description = "InvoiceEnterpriseVO对象")
public class InvoiceEnterpriseVO  implements Serializable {
    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     *支付清单ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payListId;

    /**
     * 创客支付ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payMakerId;

    /**
     * 商户名字
     */
    private String enterpriseName;

    /**
     *服务商名字
     */
    private String serviceProviderName;

    /**
     *'类型，总包+分包，众包/众采',
     */
    private String worksheetType;

    /**
     * 金额
     */
    private BigDecimal payToPlatformAmount;

    /**
     * 发票URl
     */
    private String makerVoiceUrl;

    /**
     * 完税证明URL
     */
    private String makerTaxUrl;

    /**
     * 工单名称
     */
    private String worksheetName;

    /**
     * 模式
     */
    private String worksheetMode;
    /**
     * 发布日期
     */
    private String publishDate;

    /**
     * 工单编号
     */
    private String worksheetNo;

    /**
     * 验收url
     */
    private String achievementFiles;

    /**
     * 验收金额
     */
    private String checkMoney;

    /**
     * 创客数
     */
    private Integer makerNum;
}
