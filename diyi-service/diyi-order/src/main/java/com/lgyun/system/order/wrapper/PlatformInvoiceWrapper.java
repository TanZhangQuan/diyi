package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.PlatformInvoiceEntity;
import com.lgyun.system.order.vo.PlatformInvoiceVO;

/**
 * 总包发票信息：记录服务商开具给商户的总包发票，一次开票可能多个清单一起包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public class PlatformInvoiceWrapper extends BaseEntityWrapper<PlatformInvoiceEntity, PlatformInvoiceVO> {

    public static PlatformInvoiceWrapper build() {
        return new PlatformInvoiceWrapper();
    }

    @Override
    public PlatformInvoiceVO entityVO(PlatformInvoiceEntity platformInvoice) {
        return BeanUtil.copy(platformInvoice, PlatformInvoiceVO.class);
    }

}
