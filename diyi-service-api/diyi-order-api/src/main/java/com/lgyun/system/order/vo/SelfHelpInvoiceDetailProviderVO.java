package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/7/13.
 * @time 15:29.
 */
@Data
public class SelfHelpInvoiceDetailProviderVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客或非创客开票人ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创客或非创客开票人名称
     */
    private String name;

    /**
     * 创客或非创客开票人身份证号码
     */
    private String idCardNo;

    /**
     * 创客或非创客开票人身份证号码
     */
    private String phoneNumber;

    /**
     * 创客银行卡号
     */
    private String bankNo;

}
