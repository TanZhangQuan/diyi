package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import io.swagger.annotations.ApiModel;
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

    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerEnterpriseId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long makerId;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    private String name;

    private String idcardNo;

    private String bankCardNo;

    private String bankCardVerifyStatus;

    private String onlineAggrementUrl;

    private String paperAgreementUrl;

    private SignState signState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
