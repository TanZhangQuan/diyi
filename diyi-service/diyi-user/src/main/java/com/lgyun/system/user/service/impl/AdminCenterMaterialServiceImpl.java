package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.MaterialBelong;
import com.lgyun.common.enumeration.MaterialState;
import com.lgyun.common.enumeration.MaterialType;
import com.lgyun.common.enumeration.OpenAtribute;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddOrUpdateAdminCenterMaterialDTO;
import com.lgyun.system.user.entity.AdminCenterMaterialEntity;
import com.lgyun.system.user.mapper.AdminCenterMaterialMapper;
import com.lgyun.system.user.service.IAdminCenterMaterialService;
import com.lgyun.system.user.vo.AdminCenterMaterialListVO;
import com.lgyun.system.user.vo.AdminCenterMaterialUpdateDetailVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public R<IPage<AdminCenterMaterialListVO>> queryAdminCenterMaterialList(Long serviceProviderId, IPage<AdminCenterMaterialListVO> page) {
        return R.data(page.setRecords(baseMapper.queryAdminCenterMaterialList(serviceProviderId, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateAdminCenterMaterialState(Long adminCenterMaterialId, MaterialState materialState) {

        AdminCenterMaterialEntity adminCenterMaterialEntity = getById(adminCenterMaterialId);
        if (adminCenterMaterialEntity == null) {
            return R.fail("综合业务资料不存在");
        }

        if (!(materialState.equals(adminCenterMaterialEntity.getMaterialState()))) {
            adminCenterMaterialEntity.setMaterialState(materialState);
            updateById(adminCenterMaterialEntity);
        }

        return R.success("操作成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateAdminCenterMaterial(Long serviceProviderId, AddOrUpdateAdminCenterMaterialDTO addOrUpdateAdminCenterMaterialDTO) {

        if (addOrUpdateAdminCenterMaterialDTO.getAdminCenterMaterialId() == null) {

            AdminCenterMaterialEntity adminCenterMaterialEntity = new AdminCenterMaterialEntity();
            adminCenterMaterialEntity.setRelServiceProviderId(serviceProviderId);
            adminCenterMaterialEntity.setMaterialBelong(MaterialBelong.SERVICEPROVIDER);
            adminCenterMaterialEntity.setMaterialType(MaterialType.TEMPLATE);
            adminCenterMaterialEntity.setOpenAtribute(OpenAtribute.GLOBALPUBLIC);
            BeanUtils.copyProperties(addOrUpdateAdminCenterMaterialDTO, adminCenterMaterialEntity);
            save(adminCenterMaterialEntity);

            return R.success("新建成功");

        } else {

            AdminCenterMaterialEntity adminCenterMaterialEntity = getById(addOrUpdateAdminCenterMaterialDTO.getAdminCenterMaterialId());
            if (adminCenterMaterialEntity == null) {
                return R.fail("模板不存在");
            }

            BeanUtils.copyProperties(addOrUpdateAdminCenterMaterialDTO, adminCenterMaterialEntity);
            updateById(adminCenterMaterialEntity);

            return R.success("编辑成功");

        }

    }

    @Override
    public R<AdminCenterMaterialUpdateDetailVO> queryAdminCenterMaterialUpdateDetail(Long adminCenterMaterialId) {
        return R.data(baseMapper.queryAdminCenterMaterialUpdateDetail(adminCenterMaterialId));
    }

}
