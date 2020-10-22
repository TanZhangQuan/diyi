package com.lgyun.system.user.dto.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 渠道ID
     */
    private Long agentMainId;

    /**
     * 渠道名称
     */
    private String enterpriseName;

    /**
     * 渠道创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 渠道创建结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
