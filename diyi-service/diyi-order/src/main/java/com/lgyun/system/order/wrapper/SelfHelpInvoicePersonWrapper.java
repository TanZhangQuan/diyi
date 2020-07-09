package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoicePersonEntity;
import com.lgyun.system.order.vo.SelfHelpInvoicePersonVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public class SelfHelpInvoicePersonWrapper extends BaseEntityWrapper<SelfHelpInvoicePersonEntity, SelfHelpInvoicePersonVO>  {

    public static SelfHelpInvoicePersonWrapper build() {
        return new SelfHelpInvoicePersonWrapper();
    }

	@Override
	public SelfHelpInvoicePersonVO entityVO(SelfHelpInvoicePersonEntity selfHelpInvoicePerson) {
			SelfHelpInvoicePersonVO selfHelpInvoicePersonVO = BeanUtil.copy(selfHelpInvoicePerson, SelfHelpInvoicePersonVO.class);

		return selfHelpInvoicePersonVO;
	}

}
