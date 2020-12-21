package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/10/22.
 * @time 14:31.
 */
@Data
public class AgreementEnterpriseStateAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 商户加盟
     */
    private Integer enterpriseJoinAgreement;

    /**
     * 商户价格
     */
    private Integer enterprisePriceAgreement;

    /**
     * 商户和创客的补充协议
     */
    private Integer entMakSupplementaryAgreement;

    /**
     * 商户和服务商的补充协议
     */
    private Integer serEntSupplementaryAgreement;

    /**
     * 商户业务真实性承诺函
     */
    private Integer enterprisePromise;

    /**
     * 商户状态
     */
    private AccountState enterpriseState;
}
