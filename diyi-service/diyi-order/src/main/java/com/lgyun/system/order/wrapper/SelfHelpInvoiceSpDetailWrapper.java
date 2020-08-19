package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpDetailEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceSpDetailVO;

/**
 * 服务商开票明细：是从自助开票明细中选择过来的，信息是一致的包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-19 16:10:30
 */
public class SelfHelpInvoiceSpDetailWrapper extends BaseEntityWrapper<SelfHelpInvoiceSpDetailEntity, SelfHelpInvoiceSpDetailVO> {

    public static SelfHelpInvoiceSpDetailWrapper build() {
        return new SelfHelpInvoiceSpDetailWrapper();
    }

    @Override
    public SelfHelpInvoiceSpDetailVO entityVO(SelfHelpInvoiceSpDetailEntity selfHelpInvoiceSpDetail) {
        return BeanUtil.copy(selfHelpInvoiceSpDetail, SelfHelpInvoiceSpDetailVO.class);
    }

}
