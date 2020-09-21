package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MaterialBelong;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.common.enumeration.MaterialType;
import com.lgyun.common.enumeration.OpenAtribute;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.admin.AddAdminCenterMaterialDTO;
import com.lgyun.system.user.dto.admin.UpdateAdminCenterMaterialDTO;
import com.lgyun.system.user.entity.AdminCenterMaterialEntity;
import com.lgyun.system.user.mapper.AdminCenterMaterialMapper;
import com.lgyun.system.user.service.IAdminCenterMaterialService;
import com.lgyun.system.user.vo.admin.QueryAdminCenterMaterialListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 综合业务资料表 Service 实现
 *
 * @author tzq
 * @since 2020-09-21 14:35:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class AdminCenterMaterialServiceImpl extends BaseServiceImpl<AdminCenterMaterialMapper, AdminCenterMaterialEntity> implements IAdminCenterMaterialService {

    @Override
    public R<IPage<QueryAdminCenterMaterialListVO>> queryAdminCenterMaterialList(Long serviceProviderId, IPage<QueryAdminCenterMaterialListVO> page) {
        return R.data(page.setRecords(baseMapper.queryAdminCenterMaterialList(serviceProviderId, page)));
    }

    @Override
    public R<String> updateAdminCenterMaterialState(Long adminCenterMaterialId, MaterialState materialState) {

        AdminCenterMaterialEntity adminCenterMaterialEntity = getById(adminCenterMaterialId);
        if (adminCenterMaterialEntity == null){
            return R.fail("综合业务资料不存在");
        }

        if (!(materialState.equals(adminCenterMaterialEntity.getMaterialState()))){
            adminCenterMaterialEntity.setMaterialState(materialState);
            updateById(adminCenterMaterialEntity);
        }

        return R.success("操作成功");
    }

    @Override
    public R<String> addAdminCenterMaterial(AddAdminCenterMaterialDTO addAdminCenterMaterialDTO) {

        AdminCenterMaterialEntity adminCenterMaterialEntity = new AdminCenterMaterialEntity();
        adminCenterMaterialEntity.setServiceProviderId(addAdminCenterMaterialDTO.getServiceProviderId());
        adminCenterMaterialEntity.setMaterialName(addAdminCenterMaterialDTO.getMaterialName());
        adminCenterMaterialEntity.setMaterialUrl(addAdminCenterMaterialDTO.getMaterialUrl());
        adminCenterMaterialEntity.setMaterialDesc(addAdminCenterMaterialDTO.getMaterialDesc());
        adminCenterMaterialEntity.setMaterialBelong(MaterialBelong.SERVICEPROVIDER);
        adminCenterMaterialEntity.setMaterialType(MaterialType.TEMPLATE);
        adminCenterMaterialEntity.setOpenAtribute(OpenAtribute.GLOBALPUBLIC);
        adminCenterMaterialEntity.setMaterialState(MaterialState.USEING);
        save(adminCenterMaterialEntity);

        return R.success("操作成功");
    }

    @Override
    public R<String> updateAdminCenterMaterial(UpdateAdminCenterMaterialDTO updateAdminCenterMaterialDTO) {

        AdminCenterMaterialEntity adminCenterMaterialEntity = getById(updateAdminCenterMaterialDTO.getAdminCenterMaterialId());
        if (adminCenterMaterialEntity == null){
            return R.fail("综合业务资料不存在");
        }

        adminCenterMaterialEntity.setMaterialName(updateAdminCenterMaterialDTO.getMaterialName());
        adminCenterMaterialEntity.setMaterialUrl(updateAdminCenterMaterialDTO.getMaterialUrl());
        adminCenterMaterialEntity.setMaterialDesc(updateAdminCenterMaterialDTO.getMaterialDesc());
        adminCenterMaterialEntity.setMaterialState(updateAdminCenterMaterialDTO.getMaterialState());
        updateById(adminCenterMaterialEntity);

        return R.success("编辑成功");
    }
}
