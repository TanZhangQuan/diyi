package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/8/20.
 * @time 16:59.
 */
@Data
@ApiModel(value = "EnterpriseMakerAgreementVO对象", description = "EnterpriseMakerAgreementVO对象")
public class EnterpriseMakerAgreementVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long agreementId;

    private String enterpriseName;

    private Long enterpriseId;

    private String agreementNo;

    private String name;

    private String signType;

    private String paperAgreementUrl;

    private SignState signState;

    private String createTime;
}
