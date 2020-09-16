package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.EnterpriseServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.vo.EnterpriseServiceProviderInvoiceCatalogsVO;

/**
 * 商户-服务商开票类目：记录商户在特定服务商的开票类目包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public class EnterpriseServiceProviderInvoiceCatalogsWrapper extends BaseEntityWrapper<EnterpriseServiceProviderInvoiceCatalogsEntity, EnterpriseServiceProviderInvoiceCatalogsVO> {

    public static EnterpriseServiceProviderInvoiceCatalogsWrapper build() {
        return new EnterpriseServiceProviderInvoiceCatalogsWrapper();
    }

    @Override
    public EnterpriseServiceProviderInvoiceCatalogsVO entityVO(EnterpriseServiceProviderInvoiceCatalogsEntity enterpriseProviderInvoiceCatalogs) {
        return BeanUtil.copy(enterpriseProviderInvoiceCatalogs, EnterpriseServiceProviderInvoiceCatalogsVO.class);
    }

}
