package com.lgyun.system.user.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.user.entity.EnterpriseServiceProviderEntity;
import com.lgyun.system.user.vo.EnterpriseServiceProviderVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-28 14:53:11
 */
public class EnterpriseServiceProviderWrapper extends BaseEntityWrapper<EnterpriseServiceProviderEntity, EnterpriseServiceProviderVO> {

    public static EnterpriseServiceProviderWrapper build() {
        return new EnterpriseServiceProviderWrapper();
    }

    @Override
    public EnterpriseServiceProviderVO entityVO(EnterpriseServiceProviderEntity enterpriseProvider) {
        return BeanUtil.copy(enterpriseProvider, EnterpriseServiceProviderVO.class);
    }

}
