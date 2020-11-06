package com.lgyun.system.user.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * EnterpriseVO
 *
 * @author liangfeihu
 * @since 2020/8/18 22:40
 */
@Data
public class EnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 营业执照图片地址
     */
    private String bizLicenceUrl;


    /**
     * 企业网址
     */
    private String enterpriseUrl;
}
