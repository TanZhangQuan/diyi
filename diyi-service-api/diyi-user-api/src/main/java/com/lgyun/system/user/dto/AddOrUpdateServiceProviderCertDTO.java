package com.lgyun.system.user.dto;

import com.lgyun.common.enumeration.CertificateType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 平台端---服务商管理---添加或修改服务商资格信息DTO
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class AddOrUpdateServiceProviderCertDTO implements Serializable {
    private static final long serialVersionUID = 1L;

     /**
     * 服务商资格信息编号
     */
    private Long serviceProviderCertId;

     /**
     * 服务商编号
     */
    private Long serviceProviderId;

     /**
     * 类别
     */
    @NotNull(message = "请选择类型")
    private CertificateType certificateType;

     /**
     * 资格名称
     */
    @NotBlank(message = "请输入资格名称")
    private String certificateName;

     /**
     * 资格说明
     */
    private String certificateDesc;

     /**
     * 资格证书
     */
    @NotBlank(message = "请上传资格证书")
    private String certificateMainUrl;

}
