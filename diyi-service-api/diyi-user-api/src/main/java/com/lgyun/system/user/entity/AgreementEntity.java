package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.*;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Entity
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_agreement")
public class AgreementEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 协议类别
     */
    private AgreementType agreementType;

    /**
     * 签署类型
     */
    private SignType signType;

    /**
     * 审核状态
     */
    private AuditState auditState = AuditState.APPROVED;

    /**
     * 签署状态
     */
    private SignState signState = SignState.UNSIGN;

    /**
     * 合同有效性
     */
    private ValidState validState = ValidState.VALIDING;

    /**
     * 截止日期
     */
    private Date endDatetime;

    /**
     * 协议编号
     */
    private String agreementNo;

    /**
     * 创客ID
     */
    private Long makerId;

    /**
     * 商户ID
     */
    private Long enterpriseId;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

    /**
     * 相关局ID
     */
    private Long relBureauId;

    /**
     * 渠道商ID
     */
    private Long agentMainId;

    /**
     * 合伙人ID
     */
    private Long partnerId;

    /**
     * 平台在线协议模板ID
     */
    private Long onlineAgreementTemplateId;

    /**
     * 在线协议URL
     */
    private String onlineAgreementUrl;

    /**
     * 纸质协议URL
     */
    private String paperAgreementUrl;

    /**
     * 三方在线协议URL
     */
    private String thirdOnlineAgreementUrl;

    /**
     * 纸质协议上传状态
     */
    private PaaState paperAgreementUpload;

    /**
     * 甲方签署人员
     */
    private String firstSideSignPerson;

    /**
     * 乙方签署人员
     */
    private String secondSideSignPerson;

    /**
     * 丙方签署人员
     */
    private String thirdSideSignPerson;

    /**
     * 丁方签署人员
     */
    private String fourthSideSignPerson;

}
