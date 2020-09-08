package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jun.
 * @date 2020/8/3.
 * @time 15:36.
 */
@Data
@ApiModel(value = "SelfHelpInvoiceDetailListByEnterpriseVO对象", description = "SelfHelpInvoiceDetailListByEnterpriseVO对象")
public class SelfHelpInvoiceDetailListByEnterpriseVO implements Serializable {

    /**
     * 自助开票明细id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 增值税税率
     */
    private BigDecimal valueAddedTaxRate;

    /**
     * 价税合计额
     */
    private BigDecimal chargeMoneyNum;

    /**
     * 创客姓名
     */
    private String makerName;

    /**
     * 创客身份证号码
     */
    private String makerIdcardNo;

    /**
     * 创客手机号
     */
    private String makerPhoneNumber;

    /**
     * 非创客姓名
     */
    private String invoicePeopleName;

    /**
     * 非创客身份证号码
     */
    private String invoicePeopleIdCardNo;

    /**
     * 非创客手机号
     */
    private String invoicePeoplePhoneNumber;

}
