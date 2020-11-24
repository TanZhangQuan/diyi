package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---渠道商管理---查询渠道商列表DTO
 *
 * @date 2020/10/20.
 * @time 17:40.
 */
@Data
public class AgentMainListDTO implements Serializable {

    /**
     * 渠道名称
     */
    private String agentMainName;

    /**
     * 渠道创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 渠道创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
