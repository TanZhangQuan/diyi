package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.SignState;
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
     * 商户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 创客和商户关联的Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    /**
     * 创客id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 认证状态
     */
    private CertificationState certificationState;

    /**
     * 协议状态
     */
    private CertificationState protocolAuthentication = CertificationState.UNCERTIFIED;

    /**
     * 授权状态
     */
    private SignState empowerSignState;

    /**
     * 加盟状态
     */
    private SignState joinSignState;

    /**
     * 创客名称
     */
    private String name;
}
