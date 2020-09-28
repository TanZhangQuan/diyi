package com.lgyun.system.order.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/9/18.
 * @time 15:55.
 */
@Data
public class SelfHelpInvoiceAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *自主开票主表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceId;

    /**
     *自主开票申请表id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long selfHelpInvoiceApplyId;

    /**
     *商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     *商户名字
     */
    private String enterpriseName;

    /**
     *开票清单
     */
    private String listFile;

    /**
     *众包支付模式：标准支付；扩展支付；商户代付税费
     */
    private String payType;

    /**
     *价税合计额
     */
    private String talChargeMoneyNum;

    /**
     *状态
     */
    private String currentState;

    /**
     *创建时间
     */
    private String createTime;
}
