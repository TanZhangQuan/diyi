package com.lgyun.system.user.dto.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---商户管理DTO
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class QueryEnterpriseListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 商户编号
     */
    private Long enterpriseId;

    /**
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 商户创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

    /**
     * 商户创建结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
