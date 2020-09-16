package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.BusinessPattern;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 平台端---商户管理---商户列表vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryEnterpriseDetailEnterpriseVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    //商户名称
    private String enterpriseName;

    //法人
    private String legalPersonName;

    //法人身份证
    private String legalPersonIdCard;

    //统一社会信用代码
    private String socialCreditNo;

    //营业执照
    private String bizLicenceUrl;

    //企业网址
    private String enterpriseUrl;

    //加盟合同
    private String joinContract;

    //商家承诺函
    private String commitmentLetter;

    //业务外包模式
    private BusinessPattern businessPattern;

    //综合税费率
    private BigDecimal servicePrice;

    //营销人员
    private Long salerId;

    //运营人员
    private Long runnerId;

    //联系人1姓名
    private String contact1Name;

    //联系人1职位
    private String contact1Position;

    //联系人1电话/手机
    private String contact1Phone;

    //联系人1邮箱
    private String contact1Mail;

    //联系人2姓名
    private String contact2Name;

    //联系人2职位
    private String contact2Position;

    //联系人2电话/手机
    private String contact2Phone;

    //联系人2邮箱
    private String contact2Mail;

    //公司名称
    private String invoiceEnterpriseName;

    //纳税识别号
    private String invoiceTaxNo;

    //地址
    private String invoiceAddress;

    //电话
    private String invoiceTelNo;

    //开户银行
    private String invoiceBankName;

    //账户名
    private String invoiceAccountName;

    //账号
    private String invoiceAccount;

    //用户名
    private String employeeUserName;

    //手机
    private String phoneNumber;

    //姓名
    private String workerName;

    //创客加入邀请码
    private String inviteNo;

    //账户状态
    private AccountState enterpriseState;

}
