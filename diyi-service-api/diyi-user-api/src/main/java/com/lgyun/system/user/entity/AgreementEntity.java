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
    private AuditState auditState = AuditState.EDITING;

    /**
     * 签署状态
     */
    private SignState signState = SignState.UNSIGN;

    /**
     * 合同有效性
     */
    private ValidState validState = ValidState.TOVALID;

    /**
     * 截止日期
     */
    private Date endDatetime;

    /**
     * 协议编号
     */
    private String agreementNo;

    /**
     * 甲方身份
     */
    private ObjectType partyA;

    /**
     * 甲方ID
     */
    private Long partyAId;

    /**
     * 乙方身份
     */
    private ObjectType partyB;

    /**
     * 乙方ID
     */
    private Long partyBId;

    /**
     * 平台在线协议模板ID
     */
    private Long onlineAgreementTemplateId;

    /**
     * 在线协议URL
     */
    private String agreementUrl;

}
