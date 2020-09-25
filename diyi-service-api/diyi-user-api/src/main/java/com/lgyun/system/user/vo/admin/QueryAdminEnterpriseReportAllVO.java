package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/9/23.
 * @time 14:37.
 */
@Data
public class QueryAdminEnterpriseReportAllVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 申报id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long enterpriseReportId;

    /**
     * 服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 服务商名字
     */
    private String serviceProviderName;

    /**
     * 申请次数
     */
    private Integer applyCount;
}
