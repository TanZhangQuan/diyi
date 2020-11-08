package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.VideoAudit;
import lombok.Data;

import java.io.Serializable;

/**
 * @author jun.
 * @date 2020/10/22.
 * @time 10:32.
 */
@Data
public class AgreementMakerStateAdminVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 认证状态
     */
    private String certificationState;

    /**
     * 姓名
     */
    private String name;

    /**
     * 视频
     */
    private VideoAudit videoAudit;

    /**
     * 加盟状态  1 是已加盟 0未加盟
     */
    private Integer makerJoinAgreement;

    /**
     * 补充协议 数量
     */
    private Integer entMakSupplementaryAgreement;

    /**
     * 授权协议 1 是已授权 0未授权
     */
    private Integer makerPowerAttorney;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 账户状态
     */
    private AccountState makerState;
}
