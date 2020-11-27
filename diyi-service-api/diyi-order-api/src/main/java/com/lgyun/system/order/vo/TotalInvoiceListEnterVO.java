package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CompanyInvoiceState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/8/19.
 * @time 14:38.
 */
@Data
public class TotalInvoiceListEnterVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 记录服务商开具给商户的总包发票关联的支付清单id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long platformInvoicePayListId;

    /**
     * 总包发票信息：记录服务商开具给商户的总包发票，一次开票可能多个清单一起 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoicePrintId;

    /**
     * 总包开票申请关联的支付清单 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long invoiceApplicationPayListId;

    /**
     * 开票申请：记录商户的总包开票申请 id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applicationId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 总包开票状态：未开，已开   applyState = 0 的时候看
     */
    private CompanyInvoiceState companyInvoiceState;

    /**
     *处理状态 1,申请中；2，已拒绝；3，已全额开具；4，已部分开具,5,已取消    applyState = 1 的时候看
     */
    private String applicationState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 申请状态 0 没有申请，1 申请中
     */
    private String applyState;

    /**
     * 价税合计额
     */
    private BigDecimal payToPlatformAmount;
}
