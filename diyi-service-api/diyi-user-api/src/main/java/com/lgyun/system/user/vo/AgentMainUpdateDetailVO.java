package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---渠道商管理---渠道商详情vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class AgentMainUpdateDetailVO implements Serializable {
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
     * 法人
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

    /**
     * 加盟合同
     */
    private String joinContract;

    /**
     * 渠道商承诺函(可能多张)
     */
    private String commitmentLetters;

    /**
     * 联系人1姓名
     */
    private String contact1Name;

    /**
     * 联系人1职位
     */
    private String contact1Position;

    /**
     * 联系人1电话/手机
     */
    private String contact1Phone;

    /**
     * 联系人1邮箱
     */
    private String contact1Mail;

    /**
     * 联系人2姓名
     */
    private String contact2Name;

    /**
     * 联系人2职位
     */
    private String contact2Position;

    /**
     * 联系人2电话/手机
     */
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    private String contact2Mail;

    /**
     * 公司名称
     */
    private String invoiceEnterpriseName;

    /**
     * 纳税识别号
     */
    private String invoiceTaxNo;

    /**
     * 开票资料-地址和电话
     */
    private String invoiceAddressPhone;

    /**
     * 开票资料-开户银行和账号
     */
    private String invoiceBankNameAccount;

    /**
     * 用户名
     */
    private String employeeUserName;

    /**
     * 手机号码
     */
    private String phoneNumber;

    /**
     * 姓名
     */
    private String workerName;

}
