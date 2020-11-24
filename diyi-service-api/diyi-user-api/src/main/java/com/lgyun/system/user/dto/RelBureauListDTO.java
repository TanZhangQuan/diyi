package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class RelBureauListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

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
