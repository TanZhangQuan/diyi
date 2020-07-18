package com.lgyun.system.user.vo;

import com.lgyun.system.user.entity.OnlineAgreementNeedSignEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/7/18.
 * @time 14:48.
 */
@Data
@ApiModel(value = "OnlineAgreementNeedSignVO对象", description = "OnlineAgreementNeedSignVO对象")
public class OnlineAgreementNeedSignVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long onlineAgreementNeedSignId;
    private Long onlineAggrementId;
    private String templateType;
    private String agreementTemplate;
}
