package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * EnterpriseInfoVO
 *
 * @author tzq
 * @since 2020/8/18 22:40
 */
@Data
public class EnterpriseInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 法人名称
     */
    private String legalPersonName;

    /**
     * 法人身份证
     */
    private String legalPersonIdcard;

    /**
     * 统一社会信用代码
     */
    private String socialCreditNo;

    /**
     * 营业执照
     */
    private String bizLicenceUrl;

    /**
     * 企业网址
     */
    private String enterpriseUrl;
}
