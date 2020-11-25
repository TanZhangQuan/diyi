package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

@Data
public class AgentMainInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 渠道商名称
     */
    private String agentMainName;

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
