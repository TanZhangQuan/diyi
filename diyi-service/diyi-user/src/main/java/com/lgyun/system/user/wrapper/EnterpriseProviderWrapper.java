package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.EnterpriseProviderEntity;
import com.lgyun.system.user.vo.EnterpriseProviderVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
public class EnterpriseProviderWrapper extends BaseEntityWrapper<EnterpriseProviderEntity, EnterpriseProviderVO> {

    public static EnterpriseProviderWrapper build() {
        return new EnterpriseProviderWrapper();
    }

    @Override
    public EnterpriseProviderVO entityVO(EnterpriseProviderEntity enterpriseProvider) {
        return BeanUtil.copy(enterpriseProvider, EnterpriseProviderVO.class);
    }

}
