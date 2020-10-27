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
    private String legalPersonIdCard;

    /**
     * 企业网址
     */
    private String enterpriseUrl;

    /**
     * 统一社会信用代码
     */
    private String socialCreditNo;

    /**
     * 营业执照图片地址
     */
    private String bizLicenceUrl;

    /**
     * 办公地址(快递地址）
     */
    private String workingAddress;

    /**
     * 行业分类
     */
    private String industryType;

    /**
     * 主营业务描述
     */
    private String mainBusinessDesc;

}
