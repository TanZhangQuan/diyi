package com.lgyun.system.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ServiceProviderListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 服务商创建开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    /**
     * 服务商创建结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}
