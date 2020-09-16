package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端自然人创客和商户
 * @author jun.
 * @date 2020/9/9.
 * @time 10:23.
 */
@Data
@ApiModel(value = "AgreementMakerEnterAdminVO对象", description = "AgreementMakerEnterAdminVO对象")
public class AgreementMakerEnterAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合同id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;
    /**
     * 创客id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;
    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;
    /**
     *协议编号
     */
    private String agreementNo;
    /**
     * 创客名字
     */
    private String makerName;
    /**
     * 商户名字
     */
    private String enterpriseName;
    /**
     * 在线签约合同url
     */
    private String onlineAggrementUrl;
    /**
     * 纸质协议URL
     */
    private String paperAgreementUrl;
    /**
     * 发布时间
     */
    private String createTime;
}
