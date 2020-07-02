package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CrowdSourcePayPath;
import com.lgyun.common.enumeration.EnBusinessPattern;
import com.lgyun.common.enumeration.EnCreateType;
import com.lgyun.core.mp.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Data
@NoArgsConstructor
@TableName("diyi_enterprise")
public class EnterpriseEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 外包企业（发包方）的基本信息ID
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 用户名
     */
    private String enUserName;

    /**
     * 密码
     */
    private String enUserPwd;

    /**
     * 创客加入邀请码
     */
    private String inviteNo;

    /**
     * 客户名称
     */
    private String enterpriseName;

    /**
     * 法人名称
     */
    private String legalPerson;

    /**
     * 法人身份证
     */
    private String legalPersonCard;

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
     * 收发票/税票快递【到付】联系人姓名
     */
    private String workingRelName;

    /**
     * 收发票/税票快递【到付】联系人手机号
     */
    private String workingRelPhone;

    /**
     * 开票资料-公司名称
     */
    private String invoiceEnName;

    /**
     * 开票资料-税号
     */
    private String invoiceTaxNo;

    /**
     * 开票资料-地址
     */
    private String invoiceAddress;

    /**
     * 开票资料-电话
     */
    private String invoiceTelNo;

    /**
     * 开票资料-开户银行
     */
    private String invoiceBankName;

    /**
     * 开票资料-账户名
     */
    private String invoiceAccountName;

    /**
     * 开票资料-账号
     */
    private String invoiceAccount;

    /**
     * 业务外包模式：自然人众包（3%普票），自然人总包+分包（6%专票），个体户众包（3%专票），个体户总包+分包（6%专票），个体户众包（3%普票）
     */
    private EnBusinessPattern enBusinessPattern;

    /**
     * 众包支付通路：通联支付代发，招商银行代发，系统集成代发，平台代收代付，平台预存支付
     */
    private CrowdSourcePayPath crowdSourcePayPath;

    /**
     * 综合税费率
     */
    private BigDecimal servicePrice;

    /**
     * 联系人1姓名（一般为老板/财务负责人）
     */
    private String contact1Name;

    /**
     * 联系人1职位
     */
    private String contact1Position;

    /**
     * 联系人1电话手机（必填）
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
     * 联系人2电话手机（必填）
     */
    private String contact2Phone;

    /**
     * 联系人2邮箱
     */
    private String contact2Mail;

    /**
     * 特殊需求
     */
    private String specDemmand;

    /**
     * 创建类型：平台创建，自注册
     */
    private EnCreateType enCreateType;

    /**
     * 营销人员
     */
    private Long salerId;

    /**
     * 运营人员
     */
    private Long runnerId;

    /**
     * 行业分类
     */
    private String industryType;

    /**
     * 主营业务描述
     */
    private String mainBusinessDesc;

    /**
     * 企翼网商铺地址
     */
    private String qyshopAddress;

    /**
     * 商铺ID
     */
    private Long shopId;

    /**
     * 商铺用户名
     */
    private String shopUserName;

}
