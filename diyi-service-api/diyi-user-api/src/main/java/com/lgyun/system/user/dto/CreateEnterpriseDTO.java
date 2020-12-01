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
 * 平台端---商户管理---创建商户DTO
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class CreateEnterpriseDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户名称
     */
    @NotBlank(message = "请输入商户名称")
    private String enterpriseName;

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
     * 统一社会信用代码
     */
    @NotBlank(message = "请输入统一社会信用代码")
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
     * 收件人姓名
     */
    @NotBlank(message = "请输入收件人姓名")
    private String addressName;

    /**
     * 收件人手机号
     */
    @NotBlank(message = "请输入收件人手机号")
    @Length(min = 11, max = 11, message = "请输入11位的收件人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的收件人手机号")
    private String addressPhone;

    /**
     * 省
     */
    @NotBlank(message = "请选择省份")
    private String province;

    /**
     * 市
     */
    @NotBlank(message = "请选择市")
    private String city;

    /**
     * 区
     */
    @NotBlank(message = "请选择区")
    private String area;

    /**
     * 详细地址
     */
    @NotBlank(message = "请填写详细地址")
    private String detailedAddress;

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
    @NotBlank(message = "请输入密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位的密码")
    private String employeePwd;
}
