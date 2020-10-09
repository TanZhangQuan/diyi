package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jun.
 * @date 2020/7/29.
 * @time 16:47.
 */
@Data
public class AgreementMakerWebVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 创客-商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    /**
     * 创客ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    /**
     * 商户ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 创客名称
     */
    private String name;

    /**
     * 创客身份证号码
     */
    private String idcardNo;

    /**
     * 创客银行卡号码
     */
    private String bankCardNo;

    /**
     * 创客银行卡验证状态
     */
    private String bankCardVerifyStatus;

    /**
     * 在线合同
     */
    private String onlineAggrementUrl;

    /**
     * 纸质合同
     */
    private String paperAgreementUrl;

    /**
     * 签署状态
     */
    private SignState signState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
