package com.lgyun.system.user.vo;

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
    private Long enterpriseProviderId;

    /**
     * 合同id
     */
    private Long agreementId;
    /**
     * 商户id
     */
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
     * 合同url
     */
    private String onlineAggrementUrl;

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
    private Long serviceProviderId;
}
