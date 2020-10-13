package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @date 2020/9/3.
 * @time 17:01.
 */
@Data
public class AgreementServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合同ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    /**
     * 服务商ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private String serviceProviderId;

    /**
     * 合同编号
     */
    private String agreementNo;

    /**
     * 签署类型
     */
    private SignType signType;

    /**
     * 协议类别
     */
    private AgreementType agreementType;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 创客名字
     */
    private String makerName;

    /**
     * 商户名字
     */
    private String enterpriseName;

    /**
     * 在线合同地址
     */
    private String onlineAggrementUrl;

    /**
     * 线下合同地址
     */
    private String paperAgreementUrl;

    /**
     * 签署日期
     */
    private String createTime;

    /**
     * 签署状态
     */
    private SignState signState;
}
