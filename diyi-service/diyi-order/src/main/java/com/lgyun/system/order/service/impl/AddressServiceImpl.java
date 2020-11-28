package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.dto.AddOrUpdateAddressDTO;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.mapper.AddressMapper;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.vo.AddressListVO;
import com.lgyun.system.order.vo.AddressUpdateDetailVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional(rollbackFor = Exception.class)
    public R<String> addOrUpdateAddress(AddOrUpdateAddressDTO addOrUpdateAddressDto, Long objectId, ObjectType objectType) {

        //查询默认地址
        AddressEntity defaultAddressEntity = getOne(Wrappers.<AddressEntity>query().lambda().eq(AddressEntity::getObjectId, objectId)
                .eq(AddressEntity::getObjectType, objectType)
                .eq(AddressEntity::getIsDefault, true));

        AddressEntity addressEntity;
        if (addOrUpdateAddressDto.getAddressId() != null) {
            addressEntity = getById(addOrUpdateAddressDto.getAddressId());
            if (addressEntity == null) {
                return R.fail("收货地址不存在");
            }

            if (!(addressEntity.getObjectId().equals(objectId) || addressEntity.getObjectType().equals(objectType))) {
                return R.fail("收货地址不属于当前用户");
            }

            if (defaultAddressEntity.getId().equals(addOrUpdateAddressDto.getAddressId())) {
                addOrUpdateAddressDto.setIsDefault(true);
            } else {
                if (addOrUpdateAddressDto.getIsDefault()) {
                    defaultAddressEntity.setIsDefault(false);
                    updateById(defaultAddressEntity);
                }
            }

        } else {

            if (defaultAddressEntity == null) {
                addOrUpdateAddressDto.setIsDefault(true);
            }

            if (defaultAddressEntity != null && addOrUpdateAddressDto.getIsDefault()) {
                defaultAddressEntity.setIsDefault(false);
                updateById(defaultAddressEntity);
            }

            addressEntity = new AddressEntity();
            addressEntity.setObjectId(objectId);
            addressEntity.setObjectType(objectType);
        }

        BeanUtils.copyProperties(addOrUpdateAddressDto, addressEntity);
        saveOrUpdate(addressEntity);

        return R.success("操作成功");
    }

    @Override
    public R<AddressEntity> getAddressById(Long addressId) {
        return R.data(getById(addressId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> deleteAddress(Long addressId) {

        AddressEntity addressEntity = getById(addressId);
        if (addressEntity == null) {
            return R.fail("收货地址不存在");
        }

        //新选默认收货地址
        if (addressEntity.getIsDefault()) {
            List<AddressEntity> addressEntityList = list(Wrappers.<AddressEntity>query().lambda().eq(AddressEntity::getObjectType, addressEntity.getObjectType())
                    .eq(AddressEntity::getObjectId, addressEntity.getObjectId()).orderByDesc(AddressEntity::getCreateTime));

            //移除要删除的收货地址
            addressEntityList.remove(addressEntity);

            if (!(addressEntityList.isEmpty())) {
                AddressEntity newDefaultAddressEntity = addressEntityList.get(0);
                newDefaultAddressEntity.setIsDefault(true);
                updateById(newDefaultAddressEntity);
            }
        }

        //删除收货地址
        removeById(addressId);

        return R.success("删除收货地址成功");
    }

    @Override
    public R<IPage<AddressListVO>> queryAddressList(ObjectType objectType, Long objectId, IPage<AddressListVO> page) {
        return R.data(page.setRecords(baseMapper.queryAddressList(objectType, objectId, page)));
    }

    @Override
    public R<String> setDefaultAddress(Long objectId, ObjectType objectType, Long addressId) {
        AddressEntity addressEntity = getById(addressId);
        if (addressEntity == null) {
            return R.fail("收货地址不存在");
        }

        if (!(addressEntity.getObjectId().equals(objectId) && addressEntity.getObjectType().equals(objectType))) {
            return R.fail("收货地址不属于当前用户");
        }

        if (addressEntity.getIsDefault()) {
            return R.success("设置成功");
        }

        //查询所有收货地址
        QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AddressEntity::getObjectId, objectId)
                .eq(AddressEntity::getObjectType, objectType);

        List<AddressEntity> addressEntitieList = baseMapper.selectList(queryWrapper);

        addressEntitieList.remove(addressEntity);
        //如果地址选择默认，修改其他地址非默认
        for (AddressEntity oldAddressEntity : addressEntitieList) {
            if (oldAddressEntity.getIsDefault()) {
                oldAddressEntity.setIsDefault(false);
                updateById(oldAddressEntity);
            }
        }

        addressEntity.setIsDefault(true);
        updateById(addressEntity);

        return R.success("设置成功");
    }

    @Override
    public R<AddressUpdateDetailVO> queryAddressUpdateDetail(Long addressId) {
        return R.data(baseMapper.queryAddressUpdateDetail(addressId));
    }

}
