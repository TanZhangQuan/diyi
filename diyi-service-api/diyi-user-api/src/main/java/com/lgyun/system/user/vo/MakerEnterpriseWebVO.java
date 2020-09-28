package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
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
    private Long enterpriseId;

    /**
     * 创客和商户关联的Id
     */
    private Long makerEnterpriseId;

    /**
     * 创客id
     */
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
     * 授权协议
     */
    private SignState empowerSignState;

    /**
     * 加盟协议
     */
    private SignState joinSignState;

    /**
     * 创客名称
     */
    private String name;
}
