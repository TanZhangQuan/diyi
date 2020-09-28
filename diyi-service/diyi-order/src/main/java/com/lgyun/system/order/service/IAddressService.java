package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.vo.admin.AddressListVO;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface IAddressService extends BaseService<AddressEntity> {

    /**
     * 添加/编辑收货地址
     *
     * @param addressDto
     * @param objectId
     * @return
     */
    R<String> addOrUpdateAddress(AddressDTO addressDto, Long objectId, ObjectType objectType);

    /**
     * 查询收货地址
     *
     * @param current
     * @param size
     * @param objectId
     * @param objectType
     * @param addressId
     * @return
     */
    R<IPage<AddressEntity>> findAddressMakerId(Integer current, Integer size, Long objectId, ObjectType objectType, Long addressId);

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
    R<String> updateAddress(AddressDTO addressDto);

    /**
     * 地址删除接口
     *
     * @param addressId
     * @return
     */
    R<String> deleteAddress(Long addressId);

    /**
     * 查询收货地址信息
     *
     * @param objectType
     * @param objectId
     * @param page
     * @return
     */
    R<IPage<AddressListVO>> queryAddressList(ObjectType objectType, Long objectId, IPage<AddressListVO> page);

}

