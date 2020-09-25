package com.lgyun.system.user.dto.admin;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
     * 服务商编号
     */
    private Long serviceProviderId;

     /**
     * 服务商名称
     */
    private String serviceProviderName;

     /**
     * 服务商创建开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginDate;

     /**
     * 服务商创建结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;

}
