package com.lgyun.system.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcingPayType;
import com.lgyun.common.enumeration.SelfHelpInvoiceApplyState;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/12/10.
 * @time 17:45.
 */
@Data
public class SelfInvoiceListVo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     *自助开票id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;
    /**
     *申请商户的Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long applyEnterpriseId;
    /**
     *申请商户的名称
     */
    private String applyEnterpriseName;
    /**
     *清单url
     */
    private String listFile;
    /**
     *众包支付模式
     */
    private CrowdSourcingPayType payType;
    /**
     *总需支付服务商税费=总服务税费+总开票手续费+总身份验证费，自动计算
     */
    private BigDecimal totalPayProviderFee;
    /**
     *当前状态：未申请，申请编辑中，审核中；已通过开票中；已驳回；已开票结束
     */
    private SelfHelpInvoiceApplyState applyState;
    /**
     *申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
