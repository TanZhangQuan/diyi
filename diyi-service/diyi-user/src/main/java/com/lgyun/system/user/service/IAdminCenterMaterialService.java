package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddAdminCenterMaterialDTO;
import com.lgyun.system.user.dto.UpdateAdminCenterMaterialDTO;
import com.lgyun.system.user.entity.AdminCenterMaterialEntity;
import com.lgyun.system.user.vo.AdminCenterMaterialListVO;

/**
 * 综合业务资料表 Service 接口
 *
 * @author tzq
 * @since 2020-09-21 14:35:47
 */
public interface IAdminCenterMaterialService extends BaseService<AdminCenterMaterialEntity> {

    /**
     * 根据服务商查询综合业务资料
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    R<IPage<AdminCenterMaterialListVO>> queryAdminCenterMaterialList(Long serviceProviderId, IPage<AdminCenterMaterialListVO> page);

    /**
     * 更改服务商综合业务资料状态
     *
     * @param adminCenterMaterialId
     * @param materialState
     * @return
     */
    R<String> updateAdminCenterMaterialState(Long adminCenterMaterialId, MaterialState materialState);

    /**
     * 创建服务商综合业务资料
     *
     * @param addAdminCenterMaterialDTO
     * @return
     */
    R<String> addAdminCenterMaterial(AddAdminCenterMaterialDTO addAdminCenterMaterialDTO);

    /**
     * 编辑服务商综合业务资料
     *
     * @param updateAdminCenterMaterialDTO
     * @return
     */
    R<String> updateAdminCenterMaterial(UpdateAdminCenterMaterialDTO updateAdminCenterMaterialDTO);
}

