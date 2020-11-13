package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---合伙人管理---查询所有合伙人DTO
 *
 * @author xjw
 * @date 2020-10-21
 */
@Data
public class QueryPartnerDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 合伙人编号
     */
    private Long partnerId;

    /**
     * 合伙人名称
     */
    private String name;

    /**
     * 合伙人创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 合伙人创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
