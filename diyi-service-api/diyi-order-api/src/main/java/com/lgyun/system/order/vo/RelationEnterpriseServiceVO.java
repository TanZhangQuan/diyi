package com.lgyun.system.order.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import lombok.Data;

import java.io.Serializable;

/**
 * @author .
 * @date 2020/10/24.
 * @time 17:03.
 */
@Data
public class RelationEnterpriseServiceVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *服务商id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;
    /**
     *服务商名称
     */
    private String serviceProviderName;
    /**
     * 合作状态
     */
    private CooperateStatus cooperateStatus;
}
