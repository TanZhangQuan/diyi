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
@ApiModel(value = "MakerEnterpriseWebVO对象", description = "MakerEnterpriseWebVO对象")
public class MakerEnterpriseWebVO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long enterpriseId;
    private Long makerEnterpriseId;
    private Long makerId;
    private CertificationState certificationState;
    /**
     * 协议状态
     */
    private CertificationState protocolAuthentication = CertificationState.UNCERTIFIED;
    private SignState empowerSignState;
    private SignState joinSignState;
    private String name;
}
