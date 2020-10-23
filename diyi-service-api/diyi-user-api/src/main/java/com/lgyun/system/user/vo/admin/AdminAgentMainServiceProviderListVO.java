package com.lgyun.system.user.vo.admin;

import com.lgyun.common.enumeration.CooperateStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xjw
 * @date 2020/10/20.
 * @time 16:34.
 */
@Data
public class AdminAgentMainServiceProviderListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 服务商Id
     */
    private Long serviceProviderId;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 合作状态
     */
    private CooperateStatus cooperateStatus;

    /**
     * 匹配时间
     */
    private Date startDatetime;
}