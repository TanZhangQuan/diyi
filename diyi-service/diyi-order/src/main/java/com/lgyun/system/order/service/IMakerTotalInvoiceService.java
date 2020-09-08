package com.lgyun.system.order.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.entity.MakerTotalInvoiceEntity;
import com.lgyun.system.order.vo.MakerTotalInvoiceVO;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-18 20:49:12
 */
public interface IMakerTotalInvoiceService extends BaseService<MakerTotalInvoiceEntity> {

    /**
     * 根据商户清单id查询汇总代开的发票
     */
    MakerTotalInvoiceVO getPayEnterpriseId(Long payEnterpriseId);

}

