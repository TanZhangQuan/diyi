package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.common.api.R;
import com.lgyun.system.order.dto.AddressDto;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.mapper.AddressMapper;
import com.lgyun.system.order.service.IAddressService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-08 14:32:47
 */
@Service
@AllArgsConstructor
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressEntity> implements IAddressService {

    @Override
    public R saveAddress(AddressDto addressDto, Long makerId) {
        AddressEntity addressEntity = new AddressEntity();
        BeanUtils.copyProperties(addressDto, addressEntity);
        addressEntity.setMakerId(makerId);
        addressEntity.setCreateTime(new Date());
        addressEntity.setCreateUser(makerId);
        addressEntity.setUpdateTime(new Date());
        addressEntity.setUpdateUser(makerId);
        addressEntity.setIsDeleted(0);
        addressEntity.setStatus(1);
        save(addressEntity);
        return R.success("成功");
    }

    @Override
    public R<IPage<AddressEntity>> findAddressMakerId(IPage<AddressEntity> page, Long makerId) {
        return R.data(page.setRecords(baseMapper.findAddressMakerId(page, makerId)));
    }
}
