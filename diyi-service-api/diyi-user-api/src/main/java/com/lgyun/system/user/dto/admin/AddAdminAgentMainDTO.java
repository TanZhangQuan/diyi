package com.lgyun.system.user.dto.admin;

import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.PositionName;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 平台端---渠道商管理---创建渠道商信息DTO
 *
 * @author xjw
 * @date 2020-09-9
 */
@Data
public class AddAdminAgentMainDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商名称
     */
    @NotBlank(message = "渠道名称不能为空")
    private String enterpriseName;

    /**
     * 法人
     */
    @NotBlank(message = "法人不能为空")
    private String legalPersonName;

    /**
     * 法人身份证
     */
    @NotBlank(message = "法人身份证不能为空")
    private String legalPersonIdCard;

    /**
     * 社会信誉代码
     */
    @NotBlank(message = "社会信誉代码不能为空")
    private String socialCreditNo;

    @NotBlank(message = "营业执照不能为空")
    private String bizLicenceUrl;

    /**
     * 企业网址
     */
    private String enterpriseUrl;

    /**
     * 加盟合同
     */
    @NotBlank(message = "请上传加盟合同")
    private String joinContract;

    /**
     * 商户承诺函
     */
    @NotBlank(message = "请上传商户承诺函")
    private String commitmentLetter;

    @NotBlank(message = "营销人员不能为空")
    private Long salerId;

    @NotBlank(message = "运营人员不能为空")
    private Long runnerId;

    /**
     * 联系人1姓名
     */
    @NotBlank(message = "请输入联系人1姓名")
    private String contact1Name;

    /**
     * 联系人1职位
     */
    @NotNull(message = "请输入联系人1职位")
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
    private String contact1Mail;

    /**
     * 联系人2姓名
     */
    @NotBlank(message = "请输入联系人2姓名")
    private String contact2Name;

    /**
     * 联系人2职位
     */
    @NotNull(message = "请输入联系人2职位")
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
    private String contact2Mail;

    /**
     * 开票资料-公司名称
     */
    @NotBlank(message = "开票公司名称")
    private String invoiceEnterpriseName;

    /**
     * 开票资料-税号
     */
    @NotBlank(message = "开票税号")
    private String invoiceTaxNo;

    /**
     * 开票资料-地址
     */
    @NotBlank(message = "开票地址")
    private String invoiceAddress;

    /**
     * 开票资料-电话
     */
    @NotBlank(message = "开票电话")
    @Length(min = 11, max = 11, message = "请输入11位的开票电话/手机")
    @Pattern(regexp = "[0-9]*", message = "请输入有效的开票电话/手机")
    private String invoiceTelNo;

    /**
     * 开票资料-开户银行
     */
    @NotBlank(message = "开票开户银行")
    private String invoiceBankName;

    /**
     * 开票资料-账户名
     */
    @NotBlank(message = "开票账户名")
    private String invoiceAccountName;

    /**
     * 开票资料-账号
     */
    @NotBlank(message = "开票账号")
    private String invoiceAccount;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    private String enUserName;


    @NotBlank(message = "请输入密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位新密码")
    private String enUserPwd1;

    @NotBlank(message = "请输入密码")
    @Length(min = 6, max = 18, message = "请输入长度为6-18位新密码")
    private String enUserPwd2;

    /**
     * 账户状态
     */
    private AccountState agentState = AccountState.NORMAL;
}
