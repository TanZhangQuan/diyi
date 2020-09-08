package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.BizType;
import com.lgyun.common.enumeration.InvoicePrintState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 视图实体类
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class SelfHelpInvoiceListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自助开票ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 公司名称
     */
    private String enterpriseName;

    /**
     * 开票类目
     */
    private String invoiceType;

    /**
     * 开票状态 1，开票中；2，已开票；3，开票失败
     */
    private InvoicePrintState invoicePrintState;

    /**
     * 税种：小规模，一般纳税人
     */
    private BizType bizType;

    /**
     * 总价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 流水回单URL
     */
    private String flowContractUrl;

    /**
     * 业务合同URL
     */
    private String businessContractUrl;

    /**
     * 开票时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;

}
