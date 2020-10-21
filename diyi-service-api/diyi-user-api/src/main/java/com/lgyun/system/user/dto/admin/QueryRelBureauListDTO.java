package com.lgyun.system.user.dto.admin;

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
     * 税务局编号
     */
    private Long bureauId;

    /**
     * 税务局名称
     */
    private String relBureauName;

    /**
     * 税务局创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 税务局创建结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
