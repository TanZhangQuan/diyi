package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---支付管理---服务商支付管理vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryServiceProviderListPaymentVO implements Serializable {
    private static final long serialVersionUID = 1L;

    //服务商编号
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderId;

    //服务商名称
    private String serviceProviderName;

    //是否已签合同
    private Boolean boolSignContract;

}
