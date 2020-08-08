package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceApplyEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceApplyVO;

/**
 * 自助开票申请：记录自助开票主表的申请记录情况包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-08 10:36:37
 */
public class SelfHelpInvoiceApplyWrapper extends BaseEntityWrapper<SelfHelpInvoiceApplyEntity, SelfHelpInvoiceApplyVO> {

    public static SelfHelpInvoiceApplyWrapper build() {
        return new SelfHelpInvoiceApplyWrapper();
    }

    @Override
    public SelfHelpInvoiceApplyVO entityVO(SelfHelpInvoiceApplyEntity selfHelpInvoiceApply) {
        return BeanUtil.copy(selfHelpInvoiceApply, SelfHelpInvoiceApplyVO.class);
    }

}
