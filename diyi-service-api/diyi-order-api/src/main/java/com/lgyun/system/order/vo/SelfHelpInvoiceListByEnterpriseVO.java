package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/8/3.
 * @time 15:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceListByEnterpriseVO对象", description = "SelfHelpInvoiceListByEnterpriseVO对象")
public class SelfHelpInvoiceListByEnterpriseVO implements Serializable {

    /**
     * 自助开票id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 开票清单文件
     */
    private String listFile;

    /**
     * 众包支付模式：标准支付；扩展支付；商户代付税费
     */
    private CrowdSourcingPayType payType;

    /**
     * 开票类目
     */
    private String invoiceTypes;

    /**
     * 总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算
     */
    private BigDecimal totalPayProviderFee;

    /**
     * 业务合同URL(多张)
     */
    private String businessContractUrls;

    /**
     * 支付回单URL(多张)
     */
    private String flowContractUrls;

    /**
     * 验收单URL(多张)
     */
    private String acceptPaysheetUrls;

    /**
     * 当前状态：未申请，申请编辑中，审核中；已通过开票中；已驳回；已开票结束
     */
    private SelfHelpInvoiceApplyState currentState;

    /**
     * 开票时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
