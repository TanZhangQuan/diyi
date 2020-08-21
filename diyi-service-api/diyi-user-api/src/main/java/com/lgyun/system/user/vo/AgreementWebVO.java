package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/28.
 * @time 14:29.
 */
@Data
@ApiModel(value = "AgreementWebVO对象", description = "AgreementWebVO对象")
public class AgreementWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseProviderId;

    /**
     * 合同id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;
    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     *商户编号
     */
    private String enterpriseName;

    /**
     * 合同编码
     */
    private String agreementNo;

    /**
     * 甲方签署人员
     */
    private String firstSideSignPerson;

    /**
     * 在线合同url
     */
    private String onlineAggrementUrl;

    /**
     * 纸质合同url
     */
    private String paperAgreementUrl;

    /**
     * 合同状态
     */
    private SignState signState;

    /**
     * 签署时间
     */
    private Date signDate;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;
}
