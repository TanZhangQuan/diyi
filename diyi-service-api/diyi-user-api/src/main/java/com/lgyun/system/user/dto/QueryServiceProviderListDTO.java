package com.lgyun.system.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 平台端---服务商管理DTO
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class QueryServiceProviderListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

     /**
     * 服务商名称
     */
    private String serviceProviderName;

     /**
     * 服务商创建开始时间
     */
    private Date beginDate;

     /**
     * 服务商创建结束时间
     */
    private Date endDate;

}
