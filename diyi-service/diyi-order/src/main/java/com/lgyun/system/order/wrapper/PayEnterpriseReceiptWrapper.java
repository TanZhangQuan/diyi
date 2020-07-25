package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.PayEnterpriseReceiptEntity;
import com.lgyun.system.order.vo.PayEnterpriseReceiptVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public class PayEnterpriseReceiptWrapper extends BaseEntityWrapper<PayEnterpriseReceiptEntity, PayEnterpriseReceiptVO> {

    public static PayEnterpriseReceiptWrapper build() {
        return new PayEnterpriseReceiptWrapper();
    }

    @Override
    public PayEnterpriseReceiptVO entityVO(PayEnterpriseReceiptEntity enterprisePayReceipt) {
        return BeanUtil.copy(enterprisePayReceipt, PayEnterpriseReceiptVO.class);
    }

}
