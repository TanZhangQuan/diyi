package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.entity.AddressEntity;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface IAddressService extends BaseService<AddressEntity> {

    /**
     * 新增地址
     *
     * @param addressDto
     * @param makerId
     * @return
     */
    R saveAddress(AddressDto addressDto, Long makerId);


    /**
     * 通过创客Id去查询
     *
     * @param page
     * @param makerId
     * @return
     */
    R<IPage<AddressEntity>> findAddressMakerId(IPage<AddressEntity> page, Long makerId);

    /**
     * 地址详情接口
     *
     * @param addressId
     * @return
     */
    R getAddressById(Long addressId);

    /**
     * 地址编辑接口
     *
     * @param addressDto
     * @return
     */
    R updateAddress(AddressDto addressDto);

    /**
     * 地址删除接口
     *
     * @param addressId
     * @return
     */
    R deleteAddress(Long addressId);
}

