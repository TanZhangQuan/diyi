package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entity
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_service_provider")
public class ServiceProviderEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商账户状态
     */
    private AccountState serviceProviderState = AccountState.NORMAL;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 法人代表名称
     */
    private String legalPersonName;

    /**
     * 法人身份证
     */
    private String legalPersonIdCard;

    /**
     * 统一社会信用代码
     */
    private String socialCreditNo;

    /**
     * 营业执照正本
     */
    private String bizLicenceUrl;

    /**
     * 营业执照副本
     */
    private String bizLicenceCopyUrl;

    /**
     * 企业网址
     */
    private String serviceProviderUrl;

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
    private String invoiceEnterpriseName;

    /**
     * 开票资料-税号
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
     * 业务外包产品范围：自然人众包，自然人纵波+分包，个体户众包，个体户总包+分包，个独，有限公司
     */
    private BusinessPattern businessPattern;

    /**
     * 众包支付通路：连连支付，银行支付
     */
    private CrowdSourcePayPath crowdSourcePayPath;

    /**
     * 综合税费率（默认9%）
     */
    private BigDecimal servicePrice;

    /**
     * 联系人1姓名（一般为老板/财务负责人）
     */
    private String contact1Name;

    /**
     * 联系人1职位
     */
    private PositionName contact1Position;

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
    private PositionName contact2Position;

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
    private CreateType createType;

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

}
