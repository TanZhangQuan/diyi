package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author tzq
 * @date 2020/8/20.
 * @time 16:59.
 */
@Data
public class EnterpriseMakerAgreementVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合同ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long agreementId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 商户
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseId;

    /**
     * 合同编号
     */
    private String agreementNo;

    /**
     * 名称
     */
    private String name;

    /**
     * 签署类型
     */
    private SignType signType;

    /**
     * 纸质合同
     */
    private String agreementUrl;

    /**
     * 签署状态
     */
    private SignState signState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
}
