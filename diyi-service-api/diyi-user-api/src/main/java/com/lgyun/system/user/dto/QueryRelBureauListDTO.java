package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(description = "税务局查询条件")
public class QueryRelBureauListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 相关局编号
     */
    private Long bureauId;

    /**
     * 相关局名称
     */
    private String relBureauName;

    /**
     * 相关局创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 相关局创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}
