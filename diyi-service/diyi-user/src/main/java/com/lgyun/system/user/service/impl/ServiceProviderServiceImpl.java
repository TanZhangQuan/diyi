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
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
@RequiredArgsConstructor
public class ServiceProviderServiceImpl extends BaseServiceImpl<ServiceProviderMapper, ServiceProviderEntity> implements IServiceProviderService {


    private final IUserService userService;
    private final IOrderClient orderClient;
    private final IServiceProviderAccountService serviceProviderAccountService;

    @Autowired
    @Lazy
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @Autowired
    @Lazy
    private IAgreementService agreementService;

    @Override
    public int queryCountById(Long serviceProviderId) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderEntity::getId, serviceProviderId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<ServiceProviderContactPersonVO> getContactPerson(Long serviceProviderId) {
        return R.data(baseMapper.getContactPerson(serviceProviderId));
    }

    @Override
    public R<String> addOrUpdateContactPerson(ServiceProviderContactPersonDTO serviceProviderContactPersonDto, Long serviceProviderId) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null) {
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(serviceProviderContactPersonDto, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("操作成功");
    }

    @Override
    public R<InvoiceVO> getInvoice(Long serviceProviderId) {
        return R.data(baseMapper.getInvoice(serviceProviderId));
    }

    @Override
    public R<IPage<ServiceProviderListVO>> queryServiceProviderListAdmin(QueryServiceProviderListDTO queryServiceProviderListDTO, IPage<ServiceProviderListVO> page) {

        if (queryServiceProviderListDTO.getBeginDate() != null && queryServiceProviderListDTO.getEndDate() != null) {
            if (queryServiceProviderListDTO.getBeginDate().after(queryServiceProviderListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryServiceProviderListAdmin(queryServiceProviderListDTO, page)));
    }

    @Override
    public R<ServiceProviderUpdateDetailVO> queryServiceProviderUpdateDetail(Long serviceProviderId) {
        return R.data(baseMapper.queryServiceProviderUpdateDetail(serviceProviderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createServiceProvider(AddServiceProviderDTO addServiceProviderDTO, AdminEntity adminEntity) {

        //判断服务商名称是否已存在
        int serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda().eq(ServiceProviderEntity::getServiceProviderName, addServiceProviderDTO.getServiceProviderName()));
        if (serviceProviderNum > 0) {
            return R.fail("服务商名称已存在");
        }

        //判断社会信用代码是否已存在
        serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda().eq(ServiceProviderEntity::getSocialCreditNo, addServiceProviderDTO.getSocialCreditNo()));
        if (serviceProviderNum > 0) {
            return R.fail("服务商统一社会信用代码已存在");
        }

        int serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, addServiceProviderDTO.getEmployeeUserName()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, addServiceProviderDTO.getPhoneNumber()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        //新建服务商
        ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
        serviceProviderEntity.setCreateType(CreateType.PLATFORMCREATE);
        BeanUtil.copy(addServiceProviderDTO, serviceProviderEntity);
        save(serviceProviderEntity);

        //保存服务商收款账户信息
        ServiceProviderAccountEntity serviceProviderAccountEntity = new ServiceProviderAccountEntity();
        serviceProviderAccountEntity.setServiceProviderId(serviceProviderEntity.getId());
        serviceProviderAccountEntity.setAccountType(AccountType.BANK);
        serviceProviderAccountEntity.setIsDefault(true);
        BeanUtils.copyProperties(addServiceProviderDTO, serviceProviderAccountEntity);
        serviceProviderAccountService.save(serviceProviderAccountEntity);

        //上传加盟合同
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.SERVICEPROVIDERJOINAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setFirstSideSignPerson("地衣众包平台");
        agreementEntity.setPaperAgreementUrl(addServiceProviderDTO.getJoinContract());
        agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
        agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getServiceProviderName());
        agreementService.save(agreementEntity);

        //保存收货地址
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setObjectId(serviceProviderEntity.getId());
        addressEntity.setObjectType(ObjectType.SERVICEPEOPLE);
        addressEntity.setIsDefault(true);
        BeanUtils.copyProperties(addServiceProviderDTO, addressEntity);
        orderClient.createAddress(addressEntity);

        //新建服务商员工
        User user = new User();
        user.setUserType(UserType.SERVICEPROVIDER);
        user.setAccount(addServiceProviderDTO.getEmployeeUserName());
        user.setPhone(addServiceProviderDTO.getPhoneNumber());
        userService.save(user);

        //密码加密
        addServiceProviderDTO.setEmployeePwd(DigestUtil.encrypt(addServiceProviderDTO.getEmployeePwd()));

        ServiceProviderWorkerEntity serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
        serviceProviderWorkerEntity.setUserId(user.getId());
        serviceProviderWorkerEntity.setPositionName(PositionName.MANAGEMENT);
        serviceProviderWorkerEntity.setAdminPower(true);
        BeanUtil.copy(addServiceProviderDTO, serviceProviderWorkerEntity);
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

        //上传加盟合同
        AgreementEntity agreementEntity = agreementService.getOne(Wrappers.<AgreementEntity>query().lambda()
                .eq(AgreementEntity::getServiceProviderId, serviceProviderEntity.getId())
                .eq(AgreementEntity::getAgreementType, AgreementType.SERVICEPROVIDERJOINAGREEMENT)
                .eq(AgreementEntity::getSignState, SignState.SIGNED)
                .eq(AgreementEntity::getAuditState, AuditState.APPROVED));

        if (agreementEntity == null) {
            return R.fail("服务商加盟合同不存在");
        }

        //编辑服务商员工
        BeanUtil.copy(updateServiceProviderDTO, serviceProviderWorkerEntity);
        serviceProviderWorkerService.updateById(serviceProviderWorkerEntity);

        agreementEntity.setPaperAgreementUrl(updateServiceProviderDTO.getJoinContract());
        agreementService.updateById(agreementEntity);

        BeanUtil.copy(updateServiceProviderDTO, serviceProviderEntity);
        updateById(serviceProviderEntity);

        return R.success("编辑服务商成功");
    }

    @Override
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

}
