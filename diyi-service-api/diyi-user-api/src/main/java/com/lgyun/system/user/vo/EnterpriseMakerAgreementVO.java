package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private Long agreementId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 协议编号
     */
    private Long enterpriseId;

    /**
     *
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
    private String paperAgreementUrl;

    private String onlineAggrementUrl;
    /**
     * 签署状态
     */
    private SignState signState;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "MM-dd", timezone = "GMT+8")
    private Date createTime;
}
