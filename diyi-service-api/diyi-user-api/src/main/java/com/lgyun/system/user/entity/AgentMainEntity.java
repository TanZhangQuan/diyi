package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.CreateType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 渠道商信息表 Entity
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agent_main")
public class AgentMainEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String enUserName;

    /**
     * 密码
     */
    private String enUserPwd;

    /**
     * 渠道商账户状态1，正常状态；2，冻结状态；3，非法状态。管理后台手工调整。只有正常状态才能接单和众包服务。默认为正常状态
     */
    private AccountState agentState= AccountState.NORMAL;

    /**
     * 客户名称
     */
    private String enterpriseName;

    /**
     * 法人
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
     * 营业执照副本
     */
    private String bizLicenceUrl;

    /**
     * 企业网址
     */
    private String enterpriseUrl;

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
     * 合作产品说明
     */
    private String coProductDesc;

    /**
     * 联系人1姓名
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
     * 创建方式1:平台创建，2:自注册
     */
    private CreateType createType=CreateType.PLATFORMCREATE;

    /**
     * 营销人员
     */
    private Long salerId;

    /**
     * 运营人员
     */
    private Long runnerId;

}
