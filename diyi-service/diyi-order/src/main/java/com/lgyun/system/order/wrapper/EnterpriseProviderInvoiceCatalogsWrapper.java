package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.EnterpriseProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.EnterpriseProviderInvoiceCatalogsVO;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public class EnterpriseProviderInvoiceCatalogsWrapper extends BaseEntityWrapper<EnterpriseProviderInvoiceCatalogsEntity, EnterpriseProviderInvoiceCatalogsVO> {

    public static EnterpriseProviderInvoiceCatalogsWrapper build() {
        return new EnterpriseProviderInvoiceCatalogsWrapper();
    }

    @Override
    public EnterpriseProviderInvoiceCatalogsVO entityVO(EnterpriseProviderInvoiceCatalogsEntity enterpriseProviderInvoiceCatalogs) {
        return BeanUtil.copy(enterpriseProviderInvoiceCatalogs, EnterpriseProviderInvoiceCatalogsVO.class);
    }

}
