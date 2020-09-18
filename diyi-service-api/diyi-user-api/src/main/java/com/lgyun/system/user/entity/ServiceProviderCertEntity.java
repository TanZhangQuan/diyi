package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.CertificateType;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 服务商资格信息表 Entity
 *
 * @author tzq
 * @since 2020-09-17 10:58:41
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_service_provider_cert")
public class ServiceProviderCertEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 服务商ID
     */
    private Long serviceProviderId;

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

    /**
     * 资格证书副本URL
     */
    private String certificateSupplyUrl;

    /**
     * 资格证书辅助文件URL
     */
    private String certificateOtherUrl;

}
