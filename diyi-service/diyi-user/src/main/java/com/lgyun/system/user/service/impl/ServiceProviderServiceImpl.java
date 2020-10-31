package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.dto.ServiceProviderBankCardDTO;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDTO;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDTO;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderDTO;
import com.lgyun.system.user.dto.QueryServiceProviderListDTO;
import com.lgyun.system.user.dto.AddOrUpdateServiceProviderContactDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
    public int queryCountByServiceProviderName(String serviceProviderName, Long serviceProviderId) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderEntity::getServiceProviderName, serviceProviderName)
                .ne(serviceProviderId != null, ServiceProviderEntity::getId, serviceProviderId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public int queryCountBySocialCreditNo(String socialCreditNo, Long serviceProviderId) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderEntity::getSocialCreditNo, socialCreditNo)
                .ne(serviceProviderId != null, ServiceProviderEntity::getId, serviceProviderId);
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
    public R<ServiceProviderDetailServiceProviderVO> queryServiceProviderDetailServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryServiceProviderDetailServiceProvider(serviceProviderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createOrUpdateServiceProvider(AddOrUpdateServiceProviderDTO addOrUpdateServiceProviderDTO, AdminEntity adminEntity) {

        if (addOrUpdateServiceProviderDTO.getServiceProviderId() == null) {

            //判断服务商联系人是否已存在
            if (addOrUpdateServiceProviderDTO.getContact1Phone().equals(addOrUpdateServiceProviderDTO.getContact2Phone())) {
                return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
            }

            //判断服务商名称是否已存在
            int countByServiceProviderName = queryCountByServiceProviderName(addOrUpdateServiceProviderDTO.getServiceProviderName(), null);
            if (countByServiceProviderName > 0) {
                return R.fail("名称已存在");
            }

            //判断社会信用代码是否已存在
            int countBySocialCreditNo = queryCountBySocialCreditNo(addOrUpdateServiceProviderDTO.getSocialCreditNo(), null);
            if (countBySocialCreditNo > 0) {
                return R.fail("统一社会信用代码已存在");
            }

            //判断服务商联系人1是否已存在
            ServiceProviderWorkerEntity serviceProviderWorkerEntity = serviceProviderWorkerService.findByPhoneNumber(addOrUpdateServiceProviderDTO.getContact1Phone());
            if (serviceProviderWorkerEntity != null) {
                return R.fail("联系人1电话/手机：" + addOrUpdateServiceProviderDTO.getContact1Phone() + "已存在");
            }

            //判断服务商联系人2是否已存在
            serviceProviderWorkerEntity = serviceProviderWorkerService.findByPhoneNumber(addOrUpdateServiceProviderDTO.getContact2Phone());
            if (serviceProviderWorkerEntity != null) {
                return R.fail("联系人2电话/手机：" + addOrUpdateServiceProviderDTO.getContact2Phone() + "已存在");
            }

            ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
            serviceProviderEntity.setRunnerId(adminEntity.getId());
            serviceProviderEntity.setSalerId(adminEntity.getId());
            BeanUtil.copy(addOrUpdateServiceProviderDTO, serviceProviderEntity);
            serviceProviderEntity.setCreateType(CreateType.PLATFORMCREATE);
            save(serviceProviderEntity);

            //新建联系人员工1
            User user1 = new User();
            user1.setUserType(UserType.SERVICEPROVIDER);
            user1.setAccount(addOrUpdateServiceProviderDTO.getContact1Phone());
            user1.setPhone(addOrUpdateServiceProviderDTO.getContact1Phone());
            userService.save(user1);

            serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
            serviceProviderWorkerEntity.setServiceProviderId(serviceProviderEntity.getId());
            serviceProviderWorkerEntity.setUserId(user1.getId());
            serviceProviderWorkerEntity.setWorkerName(addOrUpdateServiceProviderDTO.getContact1Name());
            serviceProviderWorkerEntity.setPositionName(addOrUpdateServiceProviderDTO.getContact1Position());
            serviceProviderWorkerEntity.setPhoneNumber(addOrUpdateServiceProviderDTO.getContact1Phone());
            serviceProviderWorkerEntity.setEmployeeUserName(addOrUpdateServiceProviderDTO.getContact1Phone());
            serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
            serviceProviderWorkerEntity.setAdminPower(true);
            serviceProviderWorkerService.save(serviceProviderWorkerEntity);

            //新建联系人员工2
            user1 = new User();
            user1.setUserType(UserType.SERVICEPROVIDER);
            user1.setAccount(addOrUpdateServiceProviderDTO.getContact2Phone());
            user1.setPhone(addOrUpdateServiceProviderDTO.getContact2Phone());
            userService.save(user1);

            serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
            serviceProviderWorkerEntity.setServiceProviderId(serviceProviderEntity.getId());
            serviceProviderWorkerEntity.setUserId(user1.getId());
            serviceProviderWorkerEntity.setWorkerName(addOrUpdateServiceProviderDTO.getContact2Name());
            serviceProviderWorkerEntity.setPositionName(addOrUpdateServiceProviderDTO.getContact2Position());
            serviceProviderWorkerEntity.setPhoneNumber(addOrUpdateServiceProviderDTO.getContact2Phone());
            serviceProviderWorkerEntity.setEmployeeUserName(addOrUpdateServiceProviderDTO.getContact2Phone());
            serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
            serviceProviderWorkerEntity.setAdminPower(true);
            serviceProviderWorkerService.save(serviceProviderWorkerEntity);

            //上传加盟合同
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.SERVICEPROVIDERJOINAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getJoinContract());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
            agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
            agreementService.save(agreementEntity);

            //上传服务商承诺函
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getCommitmentLetter());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
            agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
            agreementService.save(agreementEntity);
            
        } else {

            ServiceProviderEntity serviceProviderEntity = getById(addOrUpdateServiceProviderDTO.getServiceProviderId());
            if (serviceProviderEntity == null) {
                return R.fail("服务商不存在");
            }

            //判断商户名称是否已存在
            int countByServiceProviderName = queryCountByServiceProviderName(addOrUpdateServiceProviderDTO.getServiceProviderName(), serviceProviderEntity.getId());
            if (countByServiceProviderName > 0) {
                return R.fail("商户名称已存在");
            }

            //判断社会信用代码是否已存在
            int countBySocialCreditNo = queryCountBySocialCreditNo(addOrUpdateServiceProviderDTO.getSocialCreditNo(), serviceProviderEntity.getId());
            if (countBySocialCreditNo > 0) {
                return R.fail("统一社会信用代码已存在");
            }

            //根据联系人生成商户员工
            AddOrUpdateServiceProviderContactDTO addOrUpdateServiceProviderContactDto = new AddOrUpdateServiceProviderContactDTO();
            addOrUpdateServiceProviderContactDto.setServiceProviderId(serviceProviderEntity.getId());
            addOrUpdateServiceProviderContactDto.setContact1Name(addOrUpdateServiceProviderDTO.getContact1Name());
            addOrUpdateServiceProviderContactDto.setContact1Position(addOrUpdateServiceProviderDTO.getContact1Position());
            addOrUpdateServiceProviderContactDto.setContact1Phone(addOrUpdateServiceProviderDTO.getContact1Phone());
            addOrUpdateServiceProviderContactDto.setContact1Mail(addOrUpdateServiceProviderDTO.getContact1Mail());
            addOrUpdateServiceProviderContactDto.setContact2Name(addOrUpdateServiceProviderDTO.getContact2Name());
            addOrUpdateServiceProviderContactDto.setContact2Position(addOrUpdateServiceProviderDTO.getContact2Position());
            addOrUpdateServiceProviderContactDto.setContact2Phone(addOrUpdateServiceProviderDTO.getContact2Phone());
            addOrUpdateServiceProviderContactDto.setContact2Mail(addOrUpdateServiceProviderDTO.getContact2Mail());
            R<String> result = serviceProviderWorkerService.addOrUpdateServiceProviderContact(addOrUpdateServiceProviderContactDto, null);
            if (!(result.isSuccess())) {
                return result;
            }

            //上传或修改加盟合同
            AgreementEntity agreementEntity = agreementService.findSuccessAgreement(null, serviceProviderEntity.getId(), AgreementType.ENTERPRISEJOINAGREEMENT, null, SignState.SIGNED);
            if (agreementEntity != null) {
                agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getJoinContract());
                agreementService.updateById(agreementEntity);
            } else {
                agreementEntity = new AgreementEntity();
                agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
                agreementEntity.setSignType(SignType.PAPERAGREEMENT);
                agreementEntity.setSignState(SignState.SIGNED);
                agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getJoinContract());
                agreementEntity.setFirstSideSignPerson(adminEntity.getName());
                agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
                agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
                agreementService.save(agreementEntity);
            }

            //上传或修改商户承诺函
            agreementEntity = agreementService.findSuccessAgreement(null, serviceProviderEntity.getId(), AgreementType.OTHERAGREEMENT, AuditState.APPROVED, null);
            if (agreementEntity != null) {
                agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getCommitmentLetter());
                agreementService.updateById(agreementEntity);
            } else {
                agreementEntity = new AgreementEntity();
                agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
                agreementEntity.setSignType(SignType.PAPERAGREEMENT);
                agreementEntity.setAuditState(AuditState.APPROVED);
                agreementEntity.setPaperAgreementUrl(addOrUpdateServiceProviderDTO.getCommitmentLetter());
                agreementEntity.setFirstSideSignPerson(adminEntity.getName());
                agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
                agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
                agreementService.save(agreementEntity);
            }

            BeanUtil.copy(addOrUpdateServiceProviderDTO, serviceProviderEntity);
            updateById(serviceProviderEntity);
            
        }
        
        return R.success("操作成功");
    }

    @Override
    public R<String> updateServiceProviderState(Long serviceProviderId, AccountState serviceProviderState) {

        ServiceProviderEntity serviceProviderEntity = getById(serviceProviderId);
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        if (!(serviceProviderState.equals(serviceProviderEntity.getServiceProviderState()))) {
            serviceProviderEntity.setServiceProviderState(serviceProviderState);
            save(serviceProviderEntity);
        }

        return R.fail("更改商户状态成功");
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
