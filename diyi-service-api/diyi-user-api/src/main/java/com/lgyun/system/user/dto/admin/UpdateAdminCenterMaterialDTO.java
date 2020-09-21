package com.lgyun.system.user.dto.admin;

import com.lgyun.common.enumeration.MaterialState;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 平台端---服务商管理---编辑综合业务资料DTO
 *
 * @author tzq
 * @date 2020-09-9
 */
@Data
public class UpdateAdminCenterMaterialDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    //综合业务资料ID
    @NotBlank(message = "请输入综合业务资料编号")
    private Long adminCenterMaterialId;

    //文档名称
    @NotBlank(message = "请输入文档名称")
    private String materialName;

    //文件描述
    private String materialDesc;

    //模板
    @NotBlank(message = "请上传模板")
    private String materialUrl;

    //状态
    @NotNull(message = "请选择状态")
    private MaterialState materialState;

}
