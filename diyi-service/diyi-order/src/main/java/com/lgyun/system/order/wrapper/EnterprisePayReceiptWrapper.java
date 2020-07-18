package com.lgyun.system.order.wrapper;

import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.EnterprisePayReceiptEntity;
import com.lgyun.system.order.vo.EnterprisePayReceiptVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-07-17 20:01:13
 */
public class EnterprisePayReceiptWrapper extends BaseEntityWrapper<EnterprisePayReceiptEntity, EnterprisePayReceiptVO> {

    public static EnterprisePayReceiptWrapper build() {
        return new EnterprisePayReceiptWrapper();
    }

    @Override
    public EnterprisePayReceiptVO entityVO(EnterprisePayReceiptEntity enterprisePayReceipt) {
        EnterprisePayReceiptVO enterprisePayReceiptVO = BeanUtil.copy(enterprisePayReceipt, EnterprisePayReceiptVO.class);
        return enterprisePayReceiptVO;
    }

}
