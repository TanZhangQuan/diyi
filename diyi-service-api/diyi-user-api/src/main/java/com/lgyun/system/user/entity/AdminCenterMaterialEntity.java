package com.lgyun.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lgyun.common.enumeration.MaterialBelong;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.common.enumeration.MaterialType;
import com.lgyun.common.enumeration.OpenAtribute;
import com.lgyun.core.mp.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 综合业务资料表 Entity
 *
 * @author tzq
 * @since 2020-09-21 14:35:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("diyi_admin_center_material")
public class AdminCenterMaterialEntity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 相关服务商
     */
    private Long serviceProviderId;

    /**
     * 业务资料名称
     */
    private String materialName;

    /**
     * 文档归属
     */
    private MaterialBelong materialBelong;

    /**
     * 文档属性
     */
    private MaterialType materialType;

    /**
     * 公开属性
     */
    private OpenAtribute openAtribute;

    /**
     * 文件URL
     */
    private String materialUrl;

    /**
     * 文件概述
     */
    private String materialDesc;

    /**
     * 状态
     */
    private MaterialState materialState = MaterialState.USEING;

}
