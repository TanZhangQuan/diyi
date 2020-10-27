package com.lgyun.system.user.vo;

import com.lgyun.common.enumeration.AccountState;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.VerifyStatus;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---合伙人管理---合伙人vo
 */
@Data
public class PartnerVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合伙人ID
     */
    private Long partnerId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 电话号码
     */
    private String phoneNumber;

    /**
     * 是否实名认证
     */
    private VerifyStatus isAuth;

    /**
     * 是否签订协议
     */
    private SignState isSign;

    /**
     * 合伙人状态
     */
    private AccountState partnerState;

    /**
     * 创建时间
     */
    private Date createTime;
}
