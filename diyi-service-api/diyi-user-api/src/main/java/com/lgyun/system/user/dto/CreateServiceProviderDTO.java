package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.EnterpriseRule;
import com.lgyun.common.enumeration.MakerRule;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Set;

@Data
@ApiModel(description = "创建服务商DTO")
public class CreateServiceProviderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "服务商名称")
    @NotBlank(message = "请输入服务商名称")
    private String serviceProviderName;

    @ApiModelProperty(value = "法人")
    @NotBlank(message = "请输入法人")
    private String legalPersonName;

    @ApiModelProperty(value = "法人身份证")
    private String legalPersonIdcard;

    @ApiModelProperty(value = "统一社会信用代码")
    @NotBlank(message = "请输入统一社会信用代码")
    private String socialCreditNo;

    @ApiModelProperty(value = "营业执照")
    @NotBlank(message = "请上传营业执照")
    private String bizLicenceUrl;

    @ApiModelProperty(value = "企业网址")
    private String serviceProviderUrl;

    @ApiModelProperty(value = "收款单位名称")
    @NotBlank(message = "请输入收款单位名称")
    private String accountName;

    @ApiModelProperty(value = "收款单位账号")
    @NotBlank(message = "请输入收款单位账号")
    private String accountNo;

    @ApiModelProperty(value = "收款单位开户银行名称")
    @NotBlank(message = "请输入收款单位开户银行名称")
    private String accountBank;

    @ApiModelProperty(value = "联系人1姓名")
    @NotBlank(message = "请输入联系人1姓名")
    private String contact1Name;

    @ApiModelProperty(value = "联系人1职位")
    @NotNull(message = "请输入联系人1职位")
    private String contact1Position;

    @ApiModelProperty(value = "联系人1电话/手机")
    @NotBlank(message = "请输入联系人1电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人1电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人1电话/手机")
    private String contact1Phone;

    @ApiModelProperty(value = "联系人1邮箱")
    @NotBlank(message = "请输入联系人1邮箱")
    @Email(message = "请输入正确的联系人1邮箱")
    private String contact1Mail;

    @ApiModelProperty(value = "联系人2姓名")
    @NotBlank(message = "请输入联系人2姓名")
    private String contact2Name;

    @ApiModelProperty(value = "联系人2职位")
    @NotNull(message = "请输入联系人2职位")
    private String contact2Position;

    @ApiModelProperty(value = "联系人2电话/手机")
    @NotBlank(message = "请输入联系人2电话/手机")
    @Length(min = 11, max = 11, message = "请输入11位的联系人2电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的联系人2电话/手机")
    private String contact2Phone;

    @ApiModelProperty(value = "联系人2邮箱")
    @NotBlank(message = "请输入联系人2邮箱")
    @Email(message = "请输入正确的联系人2邮箱")
    private String contact2Mail;

    @ApiModelProperty(value = "公司名称")
    @NotBlank(message = "请输入公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "纳税识别号")
    @NotBlank(message = "请输入纳税识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票资料-地址和电话")
    @NotBlank(message = "请输入开票地址和电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票资料-开户银行和账号")
    @NotBlank(message = "请输入开票开户银行和账号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "收件人")
    @NotBlank(message = "请输入收件人")
    private String addressName;

    @ApiModelProperty(value = "收件人手机号码")
    @NotBlank(message = "请输入收件人手机号")
    @Length(min = 11, max = 11, message = "请输入11位的收件人手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的收件人手机号")
    private String addressPhone;

    @ApiModelProperty(value = "省")
    @NotBlank(message = "请选择省份")
    private String province;

    @ApiModelProperty(value = "市")
    @NotBlank(message = "请选择市")
    private String city;

    @ApiModelProperty(value = "区")
    @NotBlank(message = "请选择区")
    private String area;

    @ApiModelProperty(value = "详细地址")
    @NotBlank(message = "请输入详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "服务商-创客业务规则")
    private Set<MakerRule> makerRuleSet;

    @ApiModelProperty(value = "服务商-商户业务规则")
    private Set<EnterpriseRule> enterpriseRuleSet;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "请输入用户名")
    @Pattern(regexp = "[A-Z,a-z,0-9,-]*", message = "用户名只能包含字母和数字")
    private String employeeUserName;

    @ApiModelProperty(value = "手机号码")
    @NotBlank(message = "请输入手机号码")
    @Length(min = 11, max = 11, message = "请输入11位手机号")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "请输入姓名")
    private String workerName;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "请输入密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位的密码")
    private String employeePwd;
}
