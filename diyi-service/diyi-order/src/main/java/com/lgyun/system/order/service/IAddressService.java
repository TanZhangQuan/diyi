package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.entity.AddressEntity;

/**
 *  Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface IAddressService extends BaseService<AddressEntity> {
    /**
     * 新增地址
     */
    R saveAddress(AddressDto addressDto, Long makerId);


    /**
     * 通过创客Id去查询
     */
    R<IPage<AddressEntity>> findAddressMakerId(IPage<AddressEntity> page, Long makerId);


}

