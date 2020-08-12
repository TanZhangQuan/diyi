package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.InvoiceApplicationEntity;
import com.lgyun.system.order.vo.InvoiceApplicationVO;

/**
 * 开票申请：记录商户的总包开票申请包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-11 16:00:00
 */
public class InvoiceApplicationWrapper extends BaseEntityWrapper<InvoiceApplicationEntity, InvoiceApplicationVO> {

    public static InvoiceApplicationWrapper build() {
        return new InvoiceApplicationWrapper();
    }

    @Override
    public InvoiceApplicationVO entityVO(InvoiceApplicationEntity invoiceApplication) {
        return BeanUtil.copy(invoiceApplication, InvoiceApplicationVO.class);
    }

}
