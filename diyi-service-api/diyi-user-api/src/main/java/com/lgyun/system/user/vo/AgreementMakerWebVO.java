package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/29.
 * @time 16:47.
 */
@Data
@ApiModel(value = "AgreementMakerWebVO对象", description = "AgreementMakerWebVO对象")
public class AgreementMakerWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long makerEnterpriseId;

    private Long makerId;

    private Long enterpriseId;

    private String name;

    private String idcardNo;

    private String bankCardNo;

    private String bankCardVerifyStatus;

    private String onlineAggrementUrl;

    private SignState signState;

    private Date signDate;
}
