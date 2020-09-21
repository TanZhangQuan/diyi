package com.lgyun.system.user.dto.admin;

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
public class AddAdminCenterMaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //相关服务商ID
    @NotNull(message = "请输入服务商编号")
    private Long serviceProviderId;

    //文档名称
    @NotBlank(message = "请输入文档名称")
    private String materialName;

    //文件描述
    private String materialDesc;

    //模板
    @NotBlank(message = "请上传模板")
    private String materialUrl;

}
