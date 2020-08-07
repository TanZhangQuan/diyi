package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.InvoicePeopleType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/13.
 * @time 15:29.
 */
@Data
@ApiModel(value = "SelfHelpInvoicePayVO对象", description = "SelfHelpInvoicePayVO对象")
public class SelfHelpInvoicePayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //自主开票ID
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    //商户名称
    private String enterpriseName;

    //开票清单文件
    private String listFile;

    //开票人身份类别
    private InvoicePeopleType invoicePeopleType;

    //总价税合计额
    private BigDecimal chargeMoneyNum;

    //开票人姓名
    private String invoicePeopleName;

    //流水回单URL
    private String flowContractUrl;

    //业务合同URL
    private String businessContractUrl;

    //交付支付验收单URL
    private String deliverSheetUrl;

    //账户余额url
    private String accountBalanceUrl;

    //发生时间
    private Date createTime;
}
