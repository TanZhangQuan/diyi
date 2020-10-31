package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 服务商查询总包发票
 * @author .
 * @date 2020/9/4.
 * @time 15:28.
 */
@Data
public class InvoiceServiceLumpVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 总包申请和支付清单表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationPayListId;
    /**
     * 总包申请id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationId;
    /**
     * 支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long payEnterpriseId;
    /**
     * 商户名称
     */
    private String enterpriseName;
    /**
     * 服务商名称
     */
    private String serviceProviderName;
    /**
     * 总包申请状态
     */
    private String applicationState;
    /**
     *总包开票状态
     */
    private String companyInvoiceState;
    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 开票说明:一个支付清单，可能多张发票，一张发票可能多个支付清单
     */
    private String applicationDesc;
    /**
     * 0 未申请，1已申请
     */
    private String applyState;
    /**
     * 总包id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePrintId;
}
