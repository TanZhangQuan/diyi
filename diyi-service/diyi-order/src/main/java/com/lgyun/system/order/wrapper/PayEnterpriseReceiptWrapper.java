package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.vo.PayEnterpriseReceiptVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-18 20:49:13
 */
public class PayEnterpriseReceiptWrapper extends BaseEntityWrapper<PayEnterpriseReceiptEntity, PayEnterpriseReceiptVO> {

    public static PayEnterpriseReceiptWrapper build() {
        return new PayEnterpriseReceiptWrapper();
    }

    @Override
    public PayEnterpriseReceiptVO entityVO(PayEnterpriseReceiptEntity payEnterpriseReceipt) {
        PayEnterpriseReceiptVO payEnterpriseReceiptVO = BeanUtil.copy(payEnterpriseReceipt, PayEnterpriseReceiptVO.class);
        return payEnterpriseReceiptVO;
    }

}
