package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "渠道商详情vo")
public class AgentMainUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "渠道商ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "渠道商名称")
    private String agentMainName;

    @ApiModelProperty(value = "法人")
    private String legalPersonName;

    @ApiModelProperty(value = "法人身份证")
    private String legalPersonIdcard;

    @ApiModelProperty(value = "统一社会信用代码")
    private String socialCreditNo;

    @ApiModelProperty(value = "营业执照")
    private String bizLicenceUrl;

    @ApiModelProperty(value = "企业网址")
    private String enterpriseUrl;

    @ApiModelProperty(value = "加盟合同")
    private String joinContract;

    @ApiModelProperty(value = "渠道商承诺函(多张)")
    private String commitmentLetters;

    @ApiModelProperty(value = "联系人1姓名")
    private String contact1Name;

    @ApiModelProperty(value = "联系人1职位")
    private String contact1Position;

    @ApiModelProperty(value = "联系人1电话/手机")
    private String contact1Phone;

    @ApiModelProperty(value = "联系人1邮箱")
    private String contact1Mail;

    @ApiModelProperty(value = "联系人2姓名")
    private String contact2Name;

    @ApiModelProperty(value = "联系人2职位")
    private String contact2Position;

    @ApiModelProperty(value = "联系人2电话/手机")
    private String contact2Phone;

    @ApiModelProperty(value = "联系人2邮箱")
    private String contact2Mail;

    @ApiModelProperty(value = "公司名称")
    private String invoiceEnterpriseName;

    @ApiModelProperty(value = "纳税识别号")
    private String invoiceTaxNo;

    @ApiModelProperty(value = "开票资料-地址和电话")
    private String invoiceAddressPhone;

    @ApiModelProperty(value = "开票资料-开户银行和账号")
    private String invoiceBankNameAccount;

    @ApiModelProperty(value = "用户名")
    private String employeeUserName;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "姓名")
    private String workerName;

}
