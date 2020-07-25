package com.lgyun.system.order.wrapper;

import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.system.order.entity.SelfHelpInvoiceFeeEntity;
import com.lgyun.system.order.vo.SelfHelpInvoiceFeeVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public class SelfHelpInvoiceFeeWrapper extends BaseEntityWrapper<SelfHelpInvoiceFeeEntity, SelfHelpInvoiceFeeVO>  {

    public static SelfHelpInvoiceFeeWrapper build() {
        return new SelfHelpInvoiceFeeWrapper();
    }

	@Override
	public SelfHelpInvoiceFeeVO entityVO(SelfHelpInvoiceFeeEntity selfHelpInvoiceFee) {

		if (selfHelpInvoiceFee == null){
			return null;
		}

		return BeanUtil.copy(selfHelpInvoiceFee, SelfHelpInvoiceFeeVO.class);
	}

}
