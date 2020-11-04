package com.lgyun.system.user.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author xjw
 * @date 2020/10/20.
 * @time 16:34.
 */
@Data
public class PartnerServiceProviderListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    /**
     * 服务商名称
     */
    private String serviceProviderName;

    /**
     * 交易金额
     */
    private BigDecimal amount;

    /**
     * 服务商创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
}