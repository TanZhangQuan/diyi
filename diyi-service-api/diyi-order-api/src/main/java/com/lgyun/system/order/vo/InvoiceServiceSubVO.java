package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoiceState;
import com.lgyun.common.enumeration.MakerInvoiceType;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *服务商查询未开票分包发票
 * @author tzq
 * @date 2020/9/7.
 * @time 16:45.
 */
@Data
public class InvoiceServiceSubVO implements Serializable {
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
     * 分包开票状态
     */
    private InvoiceState subcontractingInvoiceState;

    /**
     *创客发票开票类别: 自然人汇总代开；自然人门征单开；个体户税务局代开；个体户自开；个独自开
     */
    private MakerInvoiceType makerInvoiceType;

    /**
     * 创建时间
     */
    private String createTime;
}
