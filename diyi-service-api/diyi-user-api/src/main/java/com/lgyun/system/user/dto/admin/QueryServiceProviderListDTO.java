package com.lgyun.system.user.dto.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---支付管理---服务商支付管理dto
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class QueryServiceProviderListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //服务商编号
    private Long serviceProviderId;

    //服务商名称
    private String serviceProviderName;

}