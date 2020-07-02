package com.lgyun.system.order.wrapper;


import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.support.BaseEntityWrapper;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.vo.AddressVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
public class AddressWrapper extends BaseEntityWrapper<AddressEntity, AddressVO> {

    public static AddressWrapper build() {
        return new AddressWrapper();
    }

	@Override
	public AddressVO entityVO(AddressEntity address) {
		AddressVO addressVO = BeanUtil.copy(address, AddressVO.class);
		return addressVO;
	}

}
