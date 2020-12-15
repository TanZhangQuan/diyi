package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.CustomConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.entity.ServiceProviderInvoiceCatalogsEntity;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.user.dto.CreateServiceProviderDTO;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.dto.ServiceProviderListDTO;
import com.lgyun.system.user.dto.UpdateServiceProviderDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-07-25 14:38:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderServiceImpl extends BaseServiceImpl<ServiceProviderMapper, ServiceProviderEntity> implements IServiceProviderService {

    private IOrderClient orderClient;
    private IAgreementService agreementService;
    private IServiceProviderRulesService serviceProviderRulesService;
    private IServiceProviderWorkerService serviceProviderWorkerService;
    private IServiceProviderAccountService serviceProviderAccountService;

    @Override
    public int queryCountById(Long serviceProviderId) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderEntity::getId, serviceProviderId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<ContactInfoVO> getContactPerson(Long serviceProviderId) {
        return R.data(baseMapper.getContactPerson(serviceProviderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateContactPerson(Long serviceProviderId, ContactsInfoDTO contactsInfoDTO) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null) {
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(contactsInfoDTO, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("编辑联系人成功");
    }

    @Override
    public R<InvoiceVO> queryeInvoice(Long serviceProviderId) {
        return R.data(baseMapper.queryeInvoice(serviceProviderId));
    }

    @Override
    public R<IPage<ServiceProviderListAdminVO>> queryServiceProviderList(Long agentMainId, Long relBureauId, ServiceProviderListDTO serviceProviderListDTO, IPage<ServiceProviderListAdminVO> page) {

        if (serviceProviderListDTO.getBeginDate() != null && serviceProviderListDTO.getEndDate() != null) {
            if (serviceProviderListDTO.getBeginDate().after(serviceProviderListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryServiceProviderList(agentMainId,relBureauId, serviceProviderListDTO, page)));
    }

    @Override
    public R<ServiceProviderUpdateDetailVO> queryServiceProviderUpdateDetail(Long serviceProviderId) {
        return R.data(baseMapper.queryServiceProviderUpdateDetail(serviceProviderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createServiceProvider(CreateServiceProviderDTO createServiceProviderDTO, AdminEntity adminEntity) {

        //判断服务商名称是否已存在
        int serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda().eq(ServiceProviderEntity::getServiceProviderName, createServiceProviderDTO.getServiceProviderName()));
        if (serviceProviderNum > 0) {
            return R.fail("服务商名称已存在");
        }

        //判断社会信用代码是否已存在
        serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda().eq(ServiceProviderEntity::getSocialCreditNo, createServiceProviderDTO.getSocialCreditNo()));
        if (serviceProviderNum > 0) {
            return R.fail("服务商统一社会信用代码已存在");
        }

        int serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, createServiceProviderDTO.getEmployeeUserName()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, createServiceProviderDTO.getPhoneNumber()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        //新建服务商
        ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
        serviceProviderEntity.setCreateType(CreateType.PLATFORMCREATE);
        BeanUtil.copy(createServiceProviderDTO, serviceProviderEntity);
        save(serviceProviderEntity);

        //保存服务商收款账户信息
        ServiceProviderAccountEntity serviceProviderAccountEntity = new ServiceProviderAccountEntity();
        serviceProviderAccountEntity.setServiceProviderId(serviceProviderEntity.getId());
        serviceProviderAccountEntity.setAccountType(AccountType.BANK);
        serviceProviderAccountEntity.setBoolDefault(true);
        BeanUtils.copyProperties(createServiceProviderDTO, serviceProviderAccountEntity);
        serviceProviderAccountService.save(serviceProviderAccountEntity);

        //保存收件地址
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setObjectId(serviceProviderEntity.getId());
        addressEntity.setObjectType(ObjectType.SERVICEPEOPLE);
        addressEntity.setBoolDefault(true);
        BeanUtils.copyProperties(createServiceProviderDTO, addressEntity);
        orderClient.createAddress(addressEntity);

        //新建服务商-创客业务规则，服务商-商户业务规则
        serviceProviderRulesService.addOrUpdateServiceProviderRules(serviceProviderEntity.getId(), createServiceProviderDTO.getMakerRuleHashSet(), createServiceProviderDTO.getEnterpriseRuleSet());

        //密码加密
        createServiceProviderDTO.setEmployeePwd(DigestUtil.encrypt(createServiceProviderDTO.getEmployeePwd()));

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
        serviceProviderWorkerEntity.setPositionName(PositionName.MANAGEMENT);
        serviceProviderWorkerEntity.setSuperAdmin(true);
        serviceProviderWorkerEntity.setAdminPower(true);
        BeanUtil.copy(createServiceProviderDTO, serviceProviderWorkerEntity);
        serviceProviderWorkerEntity.setServiceProviderId(serviceProviderEntity.getId());
        serviceProviderWorkerService.save(serviceProviderWorkerEntity);

        //创建默认类目:平台服务费
        ServiceProviderInvoiceCatalogsEntity platformInvoiceCatalogs = new ServiceProviderInvoiceCatalogsEntity();
        platformInvoiceCatalogs.setServiceProviderId(serviceProviderEntity.getId());
        platformInvoiceCatalogs.setApplyScope(ApplyScope.TOTAL);
        platformInvoiceCatalogs.setInvoiceCatalogName(CustomConstant.PLATFORM_SERVICE_FEE);
        orderClient.createServiceProviderInvoiceCatalogs(platformInvoiceCatalogs);

        //创建默认类目:服务费
        ServiceProviderInvoiceCatalogsEntity serviceInvoiceCatalogs = new ServiceProviderInvoiceCatalogsEntity();
        serviceInvoiceCatalogs.setServiceProviderId(serviceProviderEntity.getId());
        serviceInvoiceCatalogs.setApplyScope(ApplyScope.TOTAL);
        serviceInvoiceCatalogs.setInvoiceCatalogName(CustomConstant.SERVICE_FEE);
        orderClient.createServiceProviderInvoiceCatalogs(serviceInvoiceCatalogs);

        return R.success("新建服务商成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateServiceProvider(UpdateServiceProviderDTO updateServiceProviderDTO, AdminEntity adminEntity) {

        //查询服务商
        ServiceProviderEntity serviceProviderEntity = getById(updateServiceProviderDTO.getServiceProviderId());
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        //查询服务商管理员
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = serviceProviderWorkerService.getOne(Wrappers.<ServiceProviderWorkerEntity>query().lambda()
                .eq(ServiceProviderWorkerEntity::getServiceProviderId, serviceProviderEntity.getId())
                .isNull(ServiceProviderWorkerEntity::getUpLevelId));

        if (StringUtils.isNotBlank(updateServiceProviderDTO.getEmployeePwd())) {
            if (updateServiceProviderDTO.getEmployeePwd().length() < 6 || updateServiceProviderDTO.getEmployeePwd().length() > 18) {
                return R.fail("请输入长度为6-18位的密码");
            }

            updateServiceProviderDTO.setEmployeePwd(DigestUtil.encrypt(updateServiceProviderDTO.getEmployeePwd()));
        } else {
            updateServiceProviderDTO.setEmployeePwd(serviceProviderWorkerEntity.getEmployeePwd());
        }

        //判断服务商名称是否已存在
        int serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda()
                .eq(ServiceProviderEntity::getServiceProviderName, updateServiceProviderDTO.getServiceProviderName())
                .ne(ServiceProviderEntity::getId, serviceProviderEntity.getId()));
        if (serviceProviderNum > 0) {
            return R.fail("服务商名称已存在");
        }

        //判断社会信用代码是否已存在
        serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda()
                .eq(ServiceProviderEntity::getSocialCreditNo, updateServiceProviderDTO.getSocialCreditNo())
                .ne(ServiceProviderEntity::getId, serviceProviderEntity.getId()));
        if (serviceProviderNum > 0) {
            return R.fail("服务商统一社会信用代码已存在");
        }

        int serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda()
                .eq(ServiceProviderWorkerEntity::getEmployeeUserName, updateServiceProviderDTO.getEmployeeUserName())
                .ne(ServiceProviderWorkerEntity::getId, serviceProviderWorkerEntity.getId()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda()
                .eq(ServiceProviderWorkerEntity::getPhoneNumber, updateServiceProviderDTO.getPhoneNumber())
                .ne(ServiceProviderWorkerEntity::getId, serviceProviderWorkerEntity.getId()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        //新建服务商-创客业务规则，服务商-商户业务规则
        serviceProviderRulesService.addOrUpdateServiceProviderRules(serviceProviderEntity.getId(), updateServiceProviderDTO.getMakerRuleHashSet(), updateServiceProviderDTO.getEnterpriseRuleSet());

        //编辑服务商员工
        BeanUtil.copy(updateServiceProviderDTO, serviceProviderWorkerEntity);
        serviceProviderWorkerService.updateById(serviceProviderWorkerEntity);

        BeanUtil.copy(updateServiceProviderDTO, serviceProviderEntity);
        updateById(serviceProviderEntity);

        return R.success("编辑服务商成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateServiceProviderState(Long serviceProviderId, AccountState serviceProviderState) {

        ServiceProviderEntity serviceProviderEntity = getById(serviceProviderId);
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        if (!(serviceProviderState.equals(serviceProviderEntity.getServiceProviderState()))) {
            serviceProviderEntity.setServiceProviderState(serviceProviderState);
            updateById(serviceProviderEntity);
        }

        return R.success("更改服务商状态成功");
    }

    @Override
    public R<IPage<ServiceProviderListPaymentVO>> queryServiceProviderListPayment(String serviceProviderName, IPage<ServiceProviderListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderListPayment(serviceProviderName, page)));
    }

    @Override
    public R getServiceAll(Long serviceProviderId, String serviceProviderName, IPage<ServiceProviderEntity> page) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        if (null != serviceProviderId && StringUtils.isNotEmpty(serviceProviderName)) {
            queryWrapper.lambda().eq(ServiceProviderEntity::getId, serviceProviderId)
                    .like(ServiceProviderEntity::getServiceProviderName, serviceProviderName);
        }
        if (null != serviceProviderId && StringUtils.isEmpty(serviceProviderName)) {
            queryWrapper.lambda().eq(ServiceProviderEntity::getId, serviceProviderId);
        }
        if (StringUtils.isNotEmpty(serviceProviderName) && null == serviceProviderId) {
            queryWrapper.lambda().like(ServiceProviderEntity::getServiceProviderName, serviceProviderName);
        }
        IPage<ServiceProviderEntity> serviceProviderEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(serviceProviderEntityIPage);
    }

    @Override
    public R<IPage<ServiceProviderIdNameListVO>> queryServiceProviderIdAndNameList(Long enterpriseId, String serviceProviderName, IPage<ServiceProviderIdNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderIdAndNameList(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R<ServiceProviderDetailAgentMainVO> queryServiceProviderDetailAgentMain(Long serviceProviderId) {
        return R.data(baseMapper.queryServiceProviderDetailAgentMain(serviceProviderId));
    }

}
