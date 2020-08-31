package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.mapper.AddressMapper;
import com.lgyun.system.order.service.IAddressService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Slf4j
@Service
@AllArgsConstructor
public class AddressServiceImpl extends BaseServiceImpl<AddressMapper, AddressEntity> implements IAddressService {

    @Override
    public R<String> addOrUpdate(AddressDto addressDto, Long objectId, ObjectType objectType) {

        AddressEntity addressEntity;
        if (addressDto.getAddressId() != null) {
            addressEntity = getById(addressDto.getAddressId());
            if (addressEntity == null) {
                return R.fail("收货地址不存在");
            }
        } else {
            addressEntity = new AddressEntity();
        }

        BeanUtils.copyProperties(addressDto, addressEntity);
        addressEntity.setObjectId(objectId);
        addressEntity.setObjectType(objectType);

        if (addressDto.getAddressId() != null) {
            updateById(addressEntity);
        } else {
            save(addressEntity);
        }

        return R.success("操作成功");
    }

    @Override
    public R<IPage<AddressEntity>> findAddressMakerId(Integer current, Integer size, Long objectId, ObjectType objectType, Long addressId) {
        QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AddressEntity::getObjectId, objectId)
                .eq(AddressEntity::getObjectType, objectType)
                .eq(addressId != null, AddressEntity::getId, addressId);

        IPage<AddressEntity> page = this.page(new Page<>(current, size), queryWrapper);
        return R.data(page);
    }

    @Override
    public R<AddressEntity> getAddressById(Long addressId) {
        return R.data(getById(addressId));
    }

    @Override
    public R<String> updateAddress(AddressDto addressDto) {
        AddressEntity addressEntity = getById(addressDto.getAddressId());
        BeanUtils.copyProperties(addressDto, addressEntity);
        saveOrUpdate(addressEntity);
        return R.success("修改成功");
    }

    @Override
    public R<String> deleteAddress(Long addressId) {
        removeById(addressId);
        return R.success("删除成功");
    }
}
