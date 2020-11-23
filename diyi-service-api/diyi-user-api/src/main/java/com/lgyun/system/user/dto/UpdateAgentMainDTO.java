package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.PositionName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 平台端---渠道商管理---编辑渠道商信息DTO
 *
 * @author xjw
 * @date 2020-09-9
 */
@Data
public class UpdateAgentMainDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商ID
     */
    @NotNull(message = "请选择渠道商")
    private String agentMainId;

    /**
     * 渠道商名称
     */
    @NotBlank(message = "请输入渠道名称")
    private String agentMainName;

    /**
     * 法人
     */
    @NotBlank(message = "请输入法人")
    private String legalPersonName;

    /**
     * 法人身份证
     */
    private String legalPersonIdcard;

    /**
     * 社会信用代码
     */
    @NotBlank(message = "请输入社会信用代码")
    private String socialCreditNo;

    /**
     * 营业执照
     */
    @NotBlank(message = "请上传营业执照")
    private String bizLicenceUrl;

    /**
     * 企业网址
     */
    private String enterpriseUrl;

    /**
     * 渠道商加盟合同
     */
    @NotBlank(message = "请上传渠道商加盟合同")
    private String joinContract;

    /**
     * 渠道商承诺函(可能多张)
     */
    @NotBlank(message = "请上传渠道商承诺函")
    private String commitmentLetters;

    /**
     * 联系人1姓名
     */
    @NotBlank(message = "请输入联系人1姓名")
    private String contact1Name;

    /**
     * 联系人1职位
     */
    @NotNull(message = "请选择联系人1职位")
    private PositionName contact1Position;

    /**
     * 联系人1电话/手机
     */
    @NotBlank(message = "请输入联系人1电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人1电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人1电话/手机")
    private String contact1Phone;

    /**
     * 联系人1邮箱
     */
    @NotBlank(message = "请输入联系人1邮箱")
    @Email(message = "请输入正确的联系人1邮箱")
    private String contact1Mail;

    /**
     * 联系人2姓名
     */
    @NotBlank(message = "请输入联系人2姓名")
    private String contact2Name;

    /**
     * 联系人2职位
     */
    @NotNull(message = "请选择联系人2职位")
    private PositionName contact2Position;

    /**
     * 联系人2电话/手机
     */
    @NotBlank(message = "请输入联系人2电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人2电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人2电话/手机")
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    @NotBlank(message = "请输入联系人2邮箱")
    @Email(message = "请输入正确的联系人2邮箱")
    private String contact2Mail;

    /**
     * 公司名称
     */
    @NotBlank(message = "请输入公司名称")
    private String invoiceEnterpriseName;

    /**
     * 纳税人识别号
     */
    @NotBlank(message = "请输入纳税人识别号")
    private String invoiceTaxNo;

    /**
     * 开票资料-地址和电话
     */
    @NotBlank(message = "请输入开票地址和电话")
    private String invoiceAddressPhone;

    /**
     * 开票资料-开户银行和账号
     */
    @NotBlank(message = "请输入开票开户银行和账号")
    private String invoiceBankNameAccount;

    /**
     * 用户名
     */
    @NotBlank(message = "请输入用户名")
    private String employeeUserName;

    /**
     * 手机号码
     */
    @NotBlank(message = "请输入手机号码")
    private String phoneNumber;

    /**
     * 姓名
     */
    @NotBlank(message = "请输入姓名")
    private String workerName;

    /**
     * 密码
     */
    private String employeePwd;
}
