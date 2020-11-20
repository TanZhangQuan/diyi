package com.lgyun.system.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.system.user.entity.AdminCenterMaterialEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgyun.system.user.vo.AdminCenterMaterialListVO;
import com.lgyun.system.user.vo.AdminCenterMaterialUpdateDetailVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 综合业务资料表 Mapper
 *
 * @author tzq
 * @since 2020-09-21 14:35:47
 */
@Mapper
public interface AdminCenterMaterialMapper extends BaseMapper<AdminCenterMaterialEntity> {

    /**
     * 根据服务商查询综合业务资料
     *
     * @param serviceProviderId
     * @param page
     * @return
     */
    List<AdminCenterMaterialListVO> queryAdminCenterMaterialList(Long serviceProviderId, IPage<AdminCenterMaterialListVO> page);

    /**
     * 查询编辑服务商综合业务资料(模板管理)详情
     *
     * @param adminCenterMaterialId
     * @return
     */
    AdminCenterMaterialUpdateDetailVO queryAdminCenterMaterialUpdateDetail(Long adminCenterMaterialId);
}

