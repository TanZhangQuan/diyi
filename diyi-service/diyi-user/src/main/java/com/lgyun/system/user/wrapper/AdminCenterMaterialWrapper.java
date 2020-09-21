package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AdminCenterMaterialEntity;
import com.lgyun.system.user.vo.AdminCenterMaterialVO;

/**
 * 综合业务资料表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-09-21 14:35:47
 */
public class AdminCenterMaterialWrapper extends BaseEntityWrapper<AdminCenterMaterialEntity, AdminCenterMaterialVO> {

    public static AdminCenterMaterialWrapper build() {
        return new AdminCenterMaterialWrapper();
    }

    @Override
    public AdminCenterMaterialVO entityVO(AdminCenterMaterialEntity adminCenterMaterial) {
        return BeanUtil.copy(adminCenterMaterial, AdminCenterMaterialVO.class);
    }

}
