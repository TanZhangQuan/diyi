package com.lgyun.system.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * 平台端---渠道商管理---查询渠道商列表DTO
 *
 * @date 2020/10/20.
 * @time 17:40.
 */
@Data
public class QueryAgentMainDTO implements Serializable {

    /**
     * 渠道名称
     */
    private String agentMainName;

    /**
     * 渠道创建开始时间
     */
    private Date beginDate;

    /**
     * 渠道创建结束时间
     */
    private Date endDate;
}
