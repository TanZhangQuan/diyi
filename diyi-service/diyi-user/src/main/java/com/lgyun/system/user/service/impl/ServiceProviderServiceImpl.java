package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.*;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderWorkerService;
import com.lgyun.system.user.service.IUserService;
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
    public R<ServiceProviderBankCardVO> getBankCard(Long serviceProviderId) {
        return R.data(baseMapper.getBankCard(serviceProviderId));
    }

    @Override
    public R<String> addOrUpdateBankCard(ServiceProviderBankCardDTO serviceProviderBankCardDto, Long serviceProviderId) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null) {
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(serviceProviderBankCardDto, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("操作成功");
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
    public R<ServiceProviderInvoiceVO> getInvoice(Long serviceProviderId) {
        return R.data(baseMapper.getInvoice(serviceProviderId));
    }

    @Override
    public R<String> addOrUpdateInvoice(ServiceProviderInvoiceDTO serviceProviderInvoiceDto, Long serviceProviderId) {

        ServiceProviderEntity serviceProviderWorkerEntity = getById(serviceProviderId);
        if (serviceProviderWorkerEntity == null) {
            return R.fail("服务商不存在");
        }

        BeanUtils.copyProperties(serviceProviderInvoiceDto, serviceProviderWorkerEntity);
        updateById(serviceProviderWorkerEntity);

        return R.success("操作成功");
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
    public R<String> createOrUpdateServiceProvider(AddOrUpdateServiceProviderDTO addOrUpdateServiceProviderDTO, AdminEntity adminEntity) {

        if (addOrUpdateServiceProviderDTO.getServiceProviderId() == null) {

            if (StringUtils.isBlank(addOrUpdateServiceProviderDTO.getEmployeePwd())) {
                return R.fail("请输入密码");
            } else {

                if (addOrUpdateServiceProviderDTO.getEmployeePwd().length() <= 6 || addOrUpdateServiceProviderDTO.getEmployeePwd().length() >= 18) {
                    return R.fail("请输入长度为6-18位的密码");
                }

                addOrUpdateServiceProviderDTO.setEmployeePwd(DigestUtil.encrypt(addOrUpdateServiceProviderDTO.getEmployeePwd()));
            }

            //判断服务商名称是否已存在
            int serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda().eq(ServiceProviderEntity::getServiceProviderName, addOrUpdateServiceProviderDTO.getServiceProviderName()));
            if (serviceProviderNum > 0) {
                return R.fail("服务商名称已存在");
            }

            //判断社会信用代码是否已存在
            serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda().eq(ServiceProviderEntity::getSocialCreditNo, addOrUpdateServiceProviderDTO.getSocialCreditNo()));
            if (serviceProviderNum > 0) {
                return R.fail("服务商统一社会信用代码已存在");
            }

            int serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda().eq(ServiceProviderWorkerEntity::getEmployeeUserName, addOrUpdateServiceProviderDTO.getEmployeeUserName()));
            if (serviceProviderWorkerNum > 0) {
                return R.fail("已存在相同用户名的管理员");
            }

            serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda().eq(ServiceProviderWorkerEntity::getPhoneNumber, addOrUpdateServiceProviderDTO.getPhoneNumber()));
            if (serviceProviderWorkerNum > 0) {
                return R.fail("已存在相同手机号的管理员");
            }

            //新建服务商
            ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
            serviceProviderEntity.setRunnerId(adminEntity.getId());
            serviceProviderEntity.setSalerId(adminEntity.getId());
            serviceProviderEntity.setCreateType(CreateType.PLATFORMCREATE);
            BeanUtil.copy(addOrUpdateServiceProviderDTO, serviceProviderEntity);
            save(serviceProviderEntity);

            //新建服务商员工
            User user = new User();
            user.setUserType(UserType.SERVICEPROVIDER);
            user.setAccount(addOrUpdateServiceProviderDTO.getPhoneNumber());
            user.setPhone(addOrUpdateServiceProviderDTO.getPhoneNumber());
            userService.save(user);

            ServiceProviderWorkerEntity serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
            serviceProviderWorkerEntity.setServiceProviderId(serviceProviderEntity.getId());
            serviceProviderWorkerEntity.setUserId(user.getId());
            serviceProviderWorkerEntity.setPositionName(PositionName.MANAGEMENT);
            serviceProviderWorkerEntity.setAdminPower(true);
            BeanUtil.copy(addOrUpdateServiceProviderDTO, serviceProviderWorkerEntity);
            serviceProviderWorkerService.save(serviceProviderWorkerEntity);

            //上传加盟合同
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.SERVICEPROVIDERJOINAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setFirstSideSignPerson("地衣众包平台");
            agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getJoinContract());
            agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
            agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getServiceProviderName());
            agreementService.save(agreementEntity);

            return R.success("新建服务商成功");

        } else {

            if (StringUtils.isNotBlank(addOrUpdateServiceProviderDTO.getEmployeePwd())) {
                if (addOrUpdateServiceProviderDTO.getEmployeePwd().length() <= 6 || addOrUpdateServiceProviderDTO.getEmployeePwd().length() >= 18) {
                    return R.fail("请输入长度为6-18位的密码");
                }
            }

            //查询服务商
            ServiceProviderEntity serviceProviderEntity = getById(addOrUpdateServiceProviderDTO.getServiceProviderId());
            if (serviceProviderEntity == null) {
                return R.fail("服务商不存在");
            }

            //判断服务商名称是否已存在
            int serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda()
                    .eq(ServiceProviderEntity::getServiceProviderName, addOrUpdateServiceProviderDTO.getServiceProviderName())
                    .ne(ServiceProviderEntity::getId, serviceProviderEntity.getId()));
            if (serviceProviderNum > 0) {
                return R.fail("服务商名称已存在");
            }

            //判断社会信用代码是否已存在
            serviceProviderNum = count(Wrappers.<ServiceProviderEntity>query().lambda()
                    .eq(ServiceProviderEntity::getSocialCreditNo, addOrUpdateServiceProviderDTO.getSocialCreditNo())
                    .ne(ServiceProviderEntity::getId, serviceProviderEntity.getId()));
            if (serviceProviderNum > 0) {
                return R.fail("服务商统一社会信用代码已存在");
            }

            //查询服务商管理员
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = serviceProviderWorkerService.getOne(Wrappers.<ServiceProviderWorkerEntity>query().lambda()
                    .eq(ServiceProviderWorkerEntity::getServiceProviderId, serviceProviderEntity.getId())
                    .eq(ServiceProviderWorkerEntity::getUpLevelId, null));

            int serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda()
                    .eq(ServiceProviderWorkerEntity::getEmployeeUserName, addOrUpdateServiceProviderDTO.getEmployeeUserName())
                    .ne(ServiceProviderWorkerEntity::getId, serviceProviderWorkerEntity.getId()));
            if (serviceProviderWorkerNum > 0) {
                return R.fail("已存在相同用户名的管理员");
            }

            serviceProviderWorkerNum = serviceProviderWorkerService.count(Wrappers.<ServiceProviderWorkerEntity>query().lambda()
                    .eq(ServiceProviderWorkerEntity::getPhoneNumber, addOrUpdateServiceProviderDTO.getPhoneNumber())
                    .ne(ServiceProviderWorkerEntity::getId, serviceProviderWorkerEntity.getId()));
            if (serviceProviderWorkerNum > 0) {
                return R.fail("已存在相同手机号的管理员");
            }

            //编辑服务商员工
            serviceProviderWorkerEntity.setEmployeeUserName(addOrUpdateServiceProviderDTO.getEmployeeUserName());
            serviceProviderWorkerEntity.setPhoneNumber(addOrUpdateServiceProviderDTO.getPhoneNumber());
            serviceProviderWorkerEntity.setWorkerName(addOrUpdateServiceProviderDTO.getWorkerName());
            if (StringUtils.isNotBlank(addOrUpdateServiceProviderDTO.getEmployeePwd())) {
                serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt(addOrUpdateServiceProviderDTO.getEmployeePwd()));
            }
            serviceProviderWorkerService.updateById(serviceProviderWorkerEntity);

            //上传加盟合同
            AgreementEntity agreementEntity = agreementService.getOne(Wrappers.<AgreementEntity>query().lambda()
                    .eq(AgreementEntity::getServiceProviderId, serviceProviderEntity.getId())
                    .eq(AgreementEntity::getAgreementType, AgreementType.SERVICEPROVIDERJOINAGREEMENT)
                    .eq(AgreementEntity::getSignState, SignState.SIGNED)
                    .eq(AgreementEntity::getAuditState, AuditState.APPROVED));

            agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getJoinContract());
            agreementService.updateById(agreementEntity);

            BeanUtil.copy(addOrUpdateServiceProviderDTO, serviceProviderEntity);
            updateById(serviceProviderEntity);

            return R.success("编辑服务商成功");

        }

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

        return R.fail("更改服务商状态成功");
    }

    @Override
    public R<IPage<ServiceProviderListPaymentVO>> queryServiceProviderListPayment(String serviceProviderName, IPage<ServiceProviderListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderListPayment(serviceProviderName, page)));
    }

    @Override
    public R getServiceAll(IPage<ServiceProviderEntity> page) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        IPage<ServiceProviderEntity> serviceProviderEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(serviceProviderEntityIPage);
    }

}
