package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import io.swagger.annotations.ApiModelProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import com.lgyun.core.mp.base.BaseEntity;
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
     * 唯一性控制
     */
    @ApiModelProperty(value = "主键")
    @TableId(type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 协议类别 1,创客加盟协议；2，商户加盟协议；3，服务商加盟协议；4，渠道商加盟协议；5、合伙人加盟协议；6、园区合作协议；7、税局合作协议；8、工商合作协议；9、创客授权书；10、商户-创客补充协议；11、服务商-商户补充协议；12、创客单独税务事项委托授权书；13、创客单独支付事项委托授权书；14、其他协议
     */
    private Integer agreementType;

    /**
     * 1、纸质协议2、平台在线协议3、三方在线协议
     */
    private SignType signType;

    /**
     * 0签署中 1已完毕
     */
    private SignState signState;

    /**
     * 签署日期
     */
    private Date signDate;

    /**
     * 协议编号
     */
    private String agreementNo;

    /**
     * 顺序号
     */
    private String sequenceNo;

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
    private Long agentId;

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
    private String onlineAggrementUrl;

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
    private Integer paperAgreementUpload;

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

    /**
     * 上传日期
     */
    private Date uploadDatetime;

    /**
     * 上传人员
     */
    private String uploadPerson;
}
