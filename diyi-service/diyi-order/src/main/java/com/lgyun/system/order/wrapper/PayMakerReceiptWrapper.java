package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PayMakerReceiptEntity;
import com.lgyun.system.order.vo.PayMakerReceiptVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-04 14:20:06
 */
public class PayMakerReceiptWrapper extends BaseEntityWrapper<PayMakerReceiptEntity, PayMakerReceiptVO> {

    public static PayMakerReceiptWrapper build() {
        return new PayMakerReceiptWrapper();
    }

    @Override
    public PayMakerReceiptVO entityVO(PayMakerReceiptEntity payMakerReceipt) {
        return BeanUtil.copy(payMakerReceipt, PayMakerReceiptVO.class);
    }

}
