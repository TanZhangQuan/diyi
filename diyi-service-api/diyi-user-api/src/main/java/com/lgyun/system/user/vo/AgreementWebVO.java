package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tzq
 * @date 2020/7/28.
 * @time 14:29.
 */
@Data
public class AgreementWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseProviderId;

    /**
     * 合同ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;
    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     *商户编号
     */
    private String enterpriseName;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

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
    private String onlineAgreementUrl;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
