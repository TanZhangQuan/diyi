package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.InvoiceApplicationPayListEntity;
import com.lgyun.system.order.vo.InvoiceApplicationPayListVO;

/**
 * 总包开票申请关联的支付清单包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public class InvoiceApplicationPayListWrapper extends BaseEntityWrapper<InvoiceApplicationPayListEntity, InvoiceApplicationPayListVO> {

    public static InvoiceApplicationPayListWrapper build() {
        return new InvoiceApplicationPayListWrapper();
    }

    @Override
    public InvoiceApplicationPayListVO entityVO(InvoiceApplicationPayListEntity invoiceApplicationPayList) {
        return BeanUtil.copy(invoiceApplicationPayList, InvoiceApplicationPayListVO.class);
    }

}
