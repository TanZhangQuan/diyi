package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 关注商户创客VO
 *
 * @author tzq
 * @since 2020/6/6 00:28
 */
@Data
public class RelEnterpriseMakerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客ID
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
    private boolean boolRealNameVerify;

    /**
     * 是否已签协议
     */
    private boolean boolSign;

    /**
     * 是否个体户
     */
    private boolean boolIndividualBusiness;

    /**
     * 是否个独
     */
    private boolean boolIndividualEnterprise;

}
