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
 * @author jun.
 * @date 2020/9/3.
 * @time 17:01.
 */
@Data
@ApiModel(value = "AgreementServiceVO对象", description = "AgreementServiceVO对象")
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
     * 1、纸质协议，2、平台在线协议，3、三方在线协议，4、单方授权函（纸质），5、单方授权函（电子）
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
