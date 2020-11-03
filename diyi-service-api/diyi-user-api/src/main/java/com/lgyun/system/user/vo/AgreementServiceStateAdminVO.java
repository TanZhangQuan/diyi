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
public class AgreementServiceStateAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;
    /**
     * 服务商名称
     */
    private String serviceProviderName;
    /**
     *服务商加盟 0
     */
    private Integer serviceProviderJoinAgreement;
    /**
     *
     *服务商和商户的补充协议
     */
    private Integer serEntSupplementaryAgreement;

    /**
     * 服务商的状态
     */
    private AccountState serviceProviderState;
}
