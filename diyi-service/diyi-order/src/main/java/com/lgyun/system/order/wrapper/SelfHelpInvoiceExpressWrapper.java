package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceExpressEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceExpressVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public class SelfHelpInvoiceExpressWrapper extends BaseEntityWrapper<SelfHelpInvoiceExpressEntity, SelfHelpInvoiceExpressVO>  {

    public static SelfHelpInvoiceExpressWrapper build() {
        return new SelfHelpInvoiceExpressWrapper();
    }

	@Override
	public SelfHelpInvoiceExpressVO entityVO(SelfHelpInvoiceExpressEntity selfHelpInvoiceExpress) {
			SelfHelpInvoiceExpressVO selfHelpInvoiceExpressVO = BeanUtil.copy(selfHelpInvoiceExpress, SelfHelpInvoiceExpressVO.class);

		return selfHelpInvoiceExpressVO;
	}

}
