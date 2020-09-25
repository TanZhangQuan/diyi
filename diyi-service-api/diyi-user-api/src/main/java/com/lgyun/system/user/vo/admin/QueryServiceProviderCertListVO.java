package com.lgyun.system.user.vo.admin;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lgyun.common.enumeration.CertificateType;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---服务商管理---服务商列表vo
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class QueryServiceProviderCertListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商资格信息编号
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long serviceProviderCertId;

    /**
     * 类别
     */
    private CertificateType certificateType;

    /**
     * 资格名称
     */
    private String certificateName;

    /**
     * 资格说明
     */
    private String certificateDesc;

    /**
     * 资格证书正本URL
     */
    private String certificateMainUrl;

}
