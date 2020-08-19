package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceSpEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceSpVO;

/**
 * 自助开票-服务商：记录自助开票主表的提交给不同服务商的包装类,返回视图层所需的字段
 *
 * @author liangfeihu
 * @since 2020-08-19 16:10:30
 */
public class SelfHelpInvoiceSpWrapper extends BaseEntityWrapper<SelfHelpInvoiceSpEntity, SelfHelpInvoiceSpVO> {

    public static SelfHelpInvoiceSpWrapper build() {
        return new SelfHelpInvoiceSpWrapper();
    }

    @Override
    public SelfHelpInvoiceSpVO entityVO(SelfHelpInvoiceSpEntity selfHelpInvoiceSp) {
        return BeanUtil.copy(selfHelpInvoiceSp, SelfHelpInvoiceSpVO.class);
    }

}
