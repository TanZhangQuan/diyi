package com.lgyun.system.user.dto;

import lombok.Data;

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
     * 商户名称
     */
    private String enterpriseName;

    /**
     * 商户创建开始时间
     */
    private Date beginDate;

    /**
     * 商户创建结束时间
     */
    private Date endDate;

}
