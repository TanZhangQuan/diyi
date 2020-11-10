package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderAccountDTO;
import com.lgyun.system.user.entity.ServiceProviderAccountEntity;
import com.lgyun.system.user.mapper.ServiceProviderAccountMapper;
import com.lgyun.system.user.service.IServiceProviderAccountService;
import com.lgyun.system.user.vo.ServiceProviderAccountListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 服务商收款账户信息表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-11-08 20:57:37
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderAccountServiceImpl extends BaseServiceImpl<ServiceProviderAccountMapper, ServiceProviderAccountEntity> implements IServiceProviderAccountService {

    @Override
    public R<IPage<ServiceProviderAccountListVO>> queryServiceProviderAccountList(Long serviceProviderId, IPage<ServiceProviderAccountListVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderAccountList(serviceProviderId, page)));
    }

    @Override
    public R<String> addOrUpdateServiceProviderAccount(Long serviceProviderId, AddOrUpdateServiceProviderAccountDTO addOrUpdateServiceProviderAccountDTO) {
        //查询默认地址
        ServiceProviderAccountEntity defaultServiceProviderAccountEntity = getOne(Wrappers.<ServiceProviderAccountEntity>query().lambda()
                .eq(ServiceProviderAccountEntity::getServiceProviderId, serviceProviderId)
                .eq(ServiceProviderAccountEntity::getIsDefault, true));

        ServiceProviderAccountEntity serviceProviderAccountEntity;
        if (addOrUpdateServiceProviderAccountDTO.getServiceProviderAccountId() != null) {
            serviceProviderAccountEntity = getById(addOrUpdateServiceProviderAccountDTO.getServiceProviderAccountId());
            if (serviceProviderAccountEntity == null) {
                return R.fail("服务商收款账户不存在");
            }

            if (!(serviceProviderAccountEntity.getServiceProviderId().equals(serviceProviderId))) {
                return R.fail("服务商收款账户不属于服务商");
            }

            if (defaultServiceProviderAccountEntity.getId().equals(addOrUpdateServiceProviderAccountDTO.getServiceProviderAccountId())) {
                addOrUpdateServiceProviderAccountDTO.setIsDefault(true);
            } else {
                if (addOrUpdateServiceProviderAccountDTO.getIsDefault()) {
                    defaultServiceProviderAccountEntity.setIsDefault(false);
                    updateById(defaultServiceProviderAccountEntity);
                }
            }

        } else {

            if (defaultServiceProviderAccountEntity == null) {
                addOrUpdateServiceProviderAccountDTO.setIsDefault(true);
            }

            if (defaultServiceProviderAccountEntity != null && addOrUpdateServiceProviderAccountDTO.getIsDefault()) {
                defaultServiceProviderAccountEntity.setIsDefault(false);
                updateById(defaultServiceProviderAccountEntity);
            }

            serviceProviderAccountEntity = new ServiceProviderAccountEntity();
            serviceProviderAccountEntity.setServiceProviderId(serviceProviderId);
        }

        BeanUtils.copyProperties(addOrUpdateServiceProviderAccountDTO, serviceProviderAccountEntity);
        saveOrUpdate(serviceProviderAccountEntity);

        return R.success("操作成功");
    }

    @Override
    public R<ServiceProviderAccountListVO> queryServiceProviderAccountUpdateDetail(Long serviceProviderAccounttId) {
        return R.data(baseMapper.queryServiceProviderAccountUpdateDetail(serviceProviderAccounttId));
    }
}
