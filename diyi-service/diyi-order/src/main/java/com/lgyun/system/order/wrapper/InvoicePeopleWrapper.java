package com.lgyun.system.order.wrapper;


import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.InvoicePeopleEntity;
import com.lgyun.system.order.vo.InvoicePeopleVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
public class InvoicePeopleWrapper extends BaseEntityWrapper<InvoicePeopleEntity, InvoicePeopleVO> {

    public static InvoicePeopleWrapper build() {
        return new InvoicePeopleWrapper();
    }

	@Override
	public InvoicePeopleVO entityVO(InvoicePeopleEntity invoicePeople) {
			InvoicePeopleVO invoicePeopleVO = BeanUtil.copy(invoicePeople, InvoicePeopleVO.class);

		return invoicePeopleVO;
	}

}
