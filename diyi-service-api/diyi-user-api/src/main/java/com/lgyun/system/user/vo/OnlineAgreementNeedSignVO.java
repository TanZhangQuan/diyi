package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.TemplateType;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 14:48.
 */
@Data
public class OnlineAgreementNeedSignVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long onlineAgreementNeedSignId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long onlineAgreementTemplateId;

    private AgreementType agreementType;

    private String agreementTemplate;

    private TemplateType templateType;

    private SignState signState;

    private String onlineAggrementUrl;
}
