package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceAccountEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceAccountVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public class SelfHelpInvoiceAccountWrapper extends BaseEntityWrapper<SelfHelpInvoiceAccountEntity, SelfHelpInvoiceAccountVO>  {

    public static SelfHelpInvoiceAccountWrapper build() {
        return new SelfHelpInvoiceAccountWrapper();
    }

	@Override
	public SelfHelpInvoiceAccountVO entityVO(SelfHelpInvoiceAccountEntity selfHelpInvoiceAccount) {
			SelfHelpInvoiceAccountVO selfHelpInvoiceAccountVO = BeanUtil.copy(selfHelpInvoiceAccount, SelfHelpInvoiceAccountVO.class);

		return selfHelpInvoiceAccountVO;
	}

}
