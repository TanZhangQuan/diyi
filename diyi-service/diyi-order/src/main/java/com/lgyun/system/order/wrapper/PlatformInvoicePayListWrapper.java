package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.PlatformInvoicePayListEntity;
import com.lgyun.system.order.vo.PlatformInvoicePayListVO;

/**
 * 记录服务商开具给商户的总包发票关联的支付清单包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 14:25:28
 */
public class PlatformInvoicePayListWrapper extends BaseEntityWrapper<PlatformInvoicePayListEntity, PlatformInvoicePayListVO> {

    public static PlatformInvoicePayListWrapper build() {
        return new PlatformInvoicePayListWrapper();
    }

    @Override
    public PlatformInvoicePayListVO entityVO(PlatformInvoicePayListEntity platformInvoicePayList) {
        return BeanUtil.copy(platformInvoicePayList, PlatformInvoicePayListVO.class);
    }

}
