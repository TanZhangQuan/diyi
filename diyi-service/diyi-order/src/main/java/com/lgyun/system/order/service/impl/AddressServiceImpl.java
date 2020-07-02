package com.lgyun.system.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.mapper.AddressMapper;
import com.lgyun.system.order.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-02 16:21:19
 */
@Service("addressService")
public class AddressServiceImpl extends ServiceImpl<AddressMapper, AddressEntity> implements IAddressService {

    @Override
    public List<AddressEntity> findAddressCompanyId(Long companyId) {
        List<AddressEntity> addressCompanyId = baseMapper.findAddressCompanyId(companyId);
        return addressCompanyId;
    }
}
