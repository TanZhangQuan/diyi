package com.lgyun.system.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.vo.AddressListVO;
import com.lgyun.system.order.vo.AddressUpdateDetailVO;

/**
 * Service 接口
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
public interface IAddressService extends BaseService<AddressEntity> {

    /**
     * 添加/编辑收件地址
     *
     * @param addOrUpdateAddressDto
     * @param objectId
     * @return
     */
    R<String> addOrUpdateAddress(AddOrUpdateAddressDTO addOrUpdateAddressDto, Long objectId, ObjectType objectType);

    /**
     * 地址详情接口
     *
     * @param addressId
     * @return
     */
    R<AddressEntity> getAddressById(Long addressId);

    /**
     * 删除收件地址
     *
     * @param addressId
     * @return
     */
    R<String> deleteAddress(Long addressId);

    /**
     * 查询收件地址信息
     *
     * @param objectType
     * @param objectId
     * @param page
     * @return
     */
    R<IPage<AddressListVO>> queryAddressList(ObjectType objectType, Long objectId, IPage<AddressListVO> page);

    /**
     * 设置默认地址
     *
     * @param objectId
     * @param objectType
     * @param addressId
     * @return
     */
    R<String> setDefaultAddress(Long objectId, ObjectType objectType, Long addressId);

    /**
     * 查询编辑收件地址详情
     *
     * @param addressId
     * @return
     */
    R<AddressUpdateDetailVO> queryAddressUpdateDetail(Long addressId);

}

