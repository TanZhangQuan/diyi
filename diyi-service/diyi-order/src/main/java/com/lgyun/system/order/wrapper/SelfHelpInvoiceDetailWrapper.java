package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceDetailEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceDetailVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public class SelfHelpInvoiceDetailWrapper extends BaseEntityWrapper<SelfHelpInvoiceDetailEntity, SelfHelpInvoiceDetailVO>  {

    public static SelfHelpInvoiceDetailWrapper build() {
        return new SelfHelpInvoiceDetailWrapper();
    }

	@Override
	public SelfHelpInvoiceDetailVO entityVO(SelfHelpInvoiceDetailEntity selfHelpInvoiceDetail) {
			SelfHelpInvoiceDetailVO selfHelpInvoiceDetailVO = BeanUtil.copy(selfHelpInvoiceDetail, SelfHelpInvoiceDetailVO.class);
		return selfHelpInvoiceDetailVO;
	}

}
