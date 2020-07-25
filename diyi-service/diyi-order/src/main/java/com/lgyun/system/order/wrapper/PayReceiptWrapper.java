package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PayReceiptEntity;
import com.lgyun.system.order.vo.PayReceiptVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-25 14:38:06
 */
public class PayReceiptWrapper extends BaseEntityWrapper<PayReceiptEntity, PayReceiptVO> {

    public static PayReceiptWrapper build() {
        return new PayReceiptWrapper();
    }

    @Override
    public PayReceiptVO entityVO(PayReceiptEntity payReceipt) {

        if (payReceipt == null){
            return null;
        }

        return BeanUtil.copy(payReceipt, PayReceiptVO.class);
    }

}
