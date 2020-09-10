package com.lgyun.system.user.dto.admin;

import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---支付管理---商户支付管理dto
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class QueryEnterpriseListDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //商户编号
    private Long enterpriseId;

    //商户名称
    private String enterpriseName;

}
