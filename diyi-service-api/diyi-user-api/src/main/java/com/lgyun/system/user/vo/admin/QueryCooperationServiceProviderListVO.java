package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CooperateStatus;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台端---商户管理---合作服务商列表vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryCooperationServiceProviderListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 总包交易金额
     */
    private BigDecimal totalSubAmount;

    /**
     * 众包交易金额
     */
    private BigDecimal crowdAmount;

    /**
     * 状态
     */
    private CooperateStatus cooperateStatus;

    /**
     * 匹配时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
