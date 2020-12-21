package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CertificationState;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/8/20.
 * @time 19:50.
 */
@Data
public class MakerEnterpriseWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 创客和商户关联的ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 认证状态
     */
    private CertificationState certificationState;

    /**
     * 是否有有效的创客授权书
     */
    private Boolean boolPowerAttorney;

    /**
     * 是否有有效的创客加盟协议
     */
    private Boolean boolJoinAgreement;

    /**
     * 创客名称
     */
    private String name;
}
