package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.TemplateType;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tzq
 * @date 2020/7/18.
 * @time 14:48.
 */
@Data
public class OnlineAgreementNeedSignVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 需要签署的授权协议ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long onlineAgreementNeedSignId;

    /**
     * 平台在线协议模板ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long onlineAgreementTemplateId;

    /**
     * 合同协议类别
     */
    private AgreementType agreementType;

    /**
     * 平台在线协议模板
     */
    private String agreementTemplate;

    /**
     * 签署文件模板类型
     */
    private TemplateType templateType;

    /**
     * 签署状态
     */
    private SignState signState;

    /**
     * 在线合同协议
     */
    private String onlineAggrementUrl;
}
