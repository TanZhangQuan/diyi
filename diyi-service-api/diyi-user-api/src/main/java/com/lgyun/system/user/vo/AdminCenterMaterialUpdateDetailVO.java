package com.lgyun.system.user.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * 平台端---服务商管理---模板管理VO
 *
 * @author tzq
 * @date 2020/9/9
 */
@Data
public class AdminCenterMaterialUpdateDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 综合业务资料ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 文档名称
     */
    private String materialName;

    /**
     * 文件描述
     */
    private String materialDesc;

    /**
     * 模板
     */
    private String materialUrl;

}
