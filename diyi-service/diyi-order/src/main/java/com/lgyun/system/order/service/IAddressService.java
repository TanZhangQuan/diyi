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
    R<String> saveAddress(AddressDto addressDto, Long makerId);


    /**
     * 查询收货地址
     *
     * @param current
     * @param size
     * @param makerId
     * @return
     */
    R<IPage<AddressEntity>> findAddressMakerId(Integer current, Integer size, Long makerId);

    /**
     * 地址详情接口
     *
     * @param addressId
     * @return
     */
    R<AddressEntity> getAddressById(Long addressId);

    /**
     * 地址编辑接口
     *
     * @param addressDto
     * @return
     */
    R<String> updateAddress(AddressDto addressDto);

    /**
     * 地址删除接口
     *
     * @param addressId
     * @return
     */
    R<String> deleteAddress(Long addressId);
}

