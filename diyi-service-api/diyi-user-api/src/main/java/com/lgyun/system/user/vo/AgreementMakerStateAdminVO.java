package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.VideoAudit;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "XXXXXX")
public class AgreementMakerStateAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创客ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    @ApiModelProperty(value = "认证状态")
    private String certificationState;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "视频")
    private VideoAudit videoAudit;

    @ApiModelProperty(value = "加盟状态: 1 是已加盟 0 未加盟")
    private Integer makerJoinAgreement;

    @ApiModelProperty(value = "补充协议")
    private Integer entMakSupplementaryAgreement;

    @ApiModelProperty(value = "授权协议")
    private Integer makerPowerAttorney;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "账户状态")
    private AccountState makerState;
}
