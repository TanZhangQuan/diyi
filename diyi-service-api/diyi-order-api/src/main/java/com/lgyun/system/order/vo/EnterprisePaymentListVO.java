package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

/**
 * @author jun.
 * @date 2020/8/12.
 * @time 17:23.
 */
@Data
@ApiModel(value = "EnterprisePaymentListVO对象",description = "EnterprisePaymentListVO对象")
public class EnterprisePaymentListVO {
    private static final long serialVersionUID = 1L;
    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;
    /**
     * 服务商Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;
    /**
     * 服务商名字
     */
    private String serviceProviderName;
    /**
     * 支付清单url
     */
    private String chargeListUrl;

    /**
     * 支付回单
     */
    private String enterprisePayReceiptUrl;

    /**
     * 日期
     */
    private Date createTime;
}