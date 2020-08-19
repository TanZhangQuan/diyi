package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CertificationState;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.VerifyStatus;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/8/17.
 * @time 9:22.
 */
@Data
@ApiModel(value = "MakerWorksheetVO对象", description = "MakerWorksheetVO对象")
public class MakerWorksheetVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 创客名字
     */
    private String name;

    /**
     * 实名认证
     */
    private VerifyStatus bankCardVerifyStatus;

    /**
     * 合同
     */
    private SignState empowerSignState;

    /**
     * 协议
     */
    private SignState joinSignState;

    /**
     * 实名认证
     */
    private CertificationState realNameAuthentication = CertificationState.UNCERTIFIED;

    /**
     * 协议状态
     */
    private CertificationState protocolAuthentication = CertificationState.UNCERTIFIED;
}