package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.dto.AddOrUpdateAdminCenterMaterialDTO;
import com.lgyun.system.user.entity.AdminCenterMaterialEntity;
import com.lgyun.system.user.vo.AdminCenterMaterialListVO;
import com.lgyun.system.user.vo.AdminCenterMaterialUpdateDetailVO;

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
     * 创建或编辑服务商综合业务资料
     *
     * @param serviceProviderId
     * @param addOrUpdateAdminCenterMaterialDTO
     * @return
     */
    R<String> addOrUpdateAdminCenterMaterial(Long serviceProviderId, AddOrUpdateAdminCenterMaterialDTO addOrUpdateAdminCenterMaterialDTO);

    /**
     * 查询编辑服务商综合业务资料(模板管理)详情
     *
     * @param adminCenterMaterialId
     * @return
     */
    R<AdminCenterMaterialUpdateDetailVO> queryAdminCenterMaterialUpdateDetail(Long adminCenterMaterialId);
}

