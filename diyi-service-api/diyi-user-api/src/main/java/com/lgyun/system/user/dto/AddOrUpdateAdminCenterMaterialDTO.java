package com.lgyun.system.user.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 平台端---服务商管理---创建综合业务资料DTO
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class AddOrUpdateAdminCenterMaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 综合业务资料ID
     */
    private Long adminCenterMaterialId;

    /**
     * 业务资料名称
     */
    @NotBlank(message = "请输入文档名称")
    private String materialName;

    /**
     * 文件URL
     */
    @NotNull(message = "请上传文件")
    private String materialUrl;

    /**
     * 文件概述
     */
    private String materialDesc;

}
