package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public class SelfHelpInvoiceWrapper extends BaseEntityWrapper<SelfHelpInvoiceEntity, SelfHelpInvoiceVO>  {

    public static SelfHelpInvoiceWrapper build() {
        return new SelfHelpInvoiceWrapper();
    }

	@Override
	public SelfHelpInvoiceVO entityVO(SelfHelpInvoiceEntity selfHelpInvoice) {

		if (selfHelpInvoice == null){
			return null;
		}

		return BeanUtil.copy(selfHelpInvoice, SelfHelpInvoiceVO.class);
	}

}
