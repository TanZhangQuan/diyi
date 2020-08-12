package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.PlatformInvoiceListEntity;
import com.lgyun.system.order.vo.PlatformInvoiceListVO;

/**
 * 记录服务商开具给商户的总包发票包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
public class PlatformInvoiceListWrapper extends BaseEntityWrapper<PlatformInvoiceListEntity, PlatformInvoiceListVO> {

    public static PlatformInvoiceListWrapper build() {
        return new PlatformInvoiceListWrapper();
    }

    @Override
    public PlatformInvoiceListVO entityVO(PlatformInvoiceListEntity platformInvoiceList) {
        return BeanUtil.copy(platformInvoiceList, PlatformInvoiceListVO.class);
    }

}
