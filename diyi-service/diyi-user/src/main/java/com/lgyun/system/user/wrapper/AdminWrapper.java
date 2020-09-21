package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.AdminEntity;
import com.lgyun.system.user.vo.AdminVO;

/**
 * 平台管理员信息表包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-09-19 15:02:12
 */
public class AdminWrapper extends BaseEntityWrapper<AdminEntity, AdminVO> {

    public static AdminWrapper build() {
        return new AdminWrapper();
    }

    @Override
    public AdminVO entityVO(AdminEntity admin) {
        return BeanUtil.copy(admin, AdminVO.class);
    }

}
