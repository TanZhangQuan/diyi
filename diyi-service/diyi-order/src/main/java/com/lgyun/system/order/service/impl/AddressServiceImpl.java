package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.order.dto.AddressDTO;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.mapper.AddressMapper;
import com.lgyun.system.order.service.IAddressService;
import com.lgyun.system.order.vo.admin.AddressListVO;
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
    public R<String> addOrUpdateAddress(AddressDTO addressDto, Long objectId, ObjectType objectType) {

        AddressEntity addressEntity;
        if (addressDto.getAddressId() != null) {
            addressEntity = getById(addressDto.getAddressId());
            if (addressEntity == null) {
                return R.fail("收货地址不存在");
            }

            if (!(addressEntity.getObjectId().equals(objectId) && addressEntity.getObjectType().equals(objectType))) {
                return R.fail("收货地址不属于当前用户");
            }

            //如果地址选择默认，修改其他地址非默认
            if (addressDto.getIsDefault()) {
                //查询所有收货地址
                QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(AddressEntity::getObjectId, objectId)
                        .eq(AddressEntity::getObjectType, objectType);

                List<AddressEntity> addressEntitieList = baseMapper.selectList(queryWrapper);

                for (AddressEntity addressEntity1 : addressEntitieList) {
                    if (addressEntity1.getIsDefault()) {
                        addressEntity1.setIsDefault(false);
                        updateById(addressEntity1);
                    }
                }
            }

        } else {
            addressEntity = new AddressEntity();

            //查询所有收货地址
            QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(AddressEntity::getObjectId, objectId)
                    .eq(AddressEntity::getObjectType, objectType);

            List<AddressEntity> addressEntitieList = baseMapper.selectList(queryWrapper);

            if (addressEntitieList.isEmpty()) {
                addressEntity.setIsDefault(true);
            } else {
                if (addressDto.getIsDefault()) {
                    for (AddressEntity addressEntity1 : addressEntitieList) {
                        if (addressEntity1.getIsDefault()) {
                            addressEntity1.setIsDefault(false);
                            updateById(addressEntity1);
                        }
                    }
                }
            }
        }

        BeanUtils.copyProperties(addressDto, addressEntity);

        if (addressDto.getAddressId() != null) {
            updateById(addressEntity);
        } else {
            addressEntity.setObjectId(objectId);
            addressEntity.setObjectType(objectType);
            save(addressEntity);
        }

        return R.success("操作成功");
    }

    @Override
    public R<IPage<AddressEntity>> findAddressMakerId(Long objectId, ObjectType objectType, Long addressId, Query query) {
        QueryWrapper<AddressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AddressEntity::getObjectId, objectId)
                .eq(AddressEntity::getObjectType, objectType)
                .eq(addressId != null, AddressEntity::getId, addressId);

        IPage<AddressEntity> page = this.page(new Page<>(query.getCurrent(), query.getCurrent()), queryWrapper);
        return R.data(page);
    }

    @Override
    public R<AddressEntity> getAddressById(Long addressId) {
        return R.data(getById(addressId));
    }

    @Override
    public R<String> updateAddress(AddressDTO addressDto) {
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

    @Override
    public R<IPage<AddressListVO>> queryAddressList(ObjectType objectType, Long objectId, IPage<AddressListVO> page) {
        return R.data(page.setRecords(baseMapper.queryAddressList(objectType, objectId, page)));
    }
}
