package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author tzq
 * @date 2020/7/8.
 * @time 14:36.
 */
@Data
@ApiModel(value = "AcceptPayMakerListVO对象", description = "AcceptPayMakerListVO对象")
public class PayEnterpriseMakerDetailListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 身份证号码
     */
    private String idcardNo;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 银行卡号
     */
    private String bankCardNo;

    /**
     * 是否实名
     */
    private Boolean boolRealNameVerify;

    /**
     * 是否已签协议
     */
    private Boolean boolSign;

    /**
     * 工作成果附件
     */
    private Boolean boolAchievement;

    /**
     * 验收金额
     */
    private BigDecimal checkMoney;

    /**
     * 验收单URL
     */
    private String acceptPaysheetUrl;

}
