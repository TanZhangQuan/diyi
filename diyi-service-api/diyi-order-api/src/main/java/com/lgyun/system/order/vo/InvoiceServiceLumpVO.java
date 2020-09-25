package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务商查询总包发票
 * @author .
 * @date 2020/9/4.
 * @time 15:28.
 */
@Data
@ApiModel(value = "InvoiceServiceLumpVO对象", description = "InvoiceServiceLumpVO对象")
public class InvoiceServiceLumpVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;

    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 总包申请id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 清单url
     */
    private String chargeListUrl;

    /**
     * 是否有支付回单
     */
    private String isPayEnterpriseReceipt;

    /**
     * 是否申请
     */
    private String isApplication;

    /**
     * 开票状态
     */
    private InvoiceState companyInvoiceState;

    /**
     * 开票说明:一个支付清单，可能多张发票，一张发票可能多个支付清单
     */
    private String invoiceDemondDesc;

    /**
     * 创建时间
     */
    private String createTime;
}
