package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.ServiceProviderBankCardDTO;
import com.lgyun.system.user.dto.ServiceProviderContactPersonDTO;
import com.lgyun.system.user.dto.ServiceProviderInvoiceDTO;
import com.lgyun.system.user.dto.admin.AddServiceProviderDTO;
import com.lgyun.system.user.dto.admin.QueryServiceProviderListDTO;
import com.lgyun.system.user.dto.admin.UpdateServiceProviderDTO;
import com.lgyun.system.user.dto.serviceProvider.AddOrUpdateServiceProviderContactDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.ServiceProviderMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.EnterprisesVO;
import com.lgyun.system.user.vo.ServiceProviderBankCardVO;
import com.lgyun.system.user.vo.ServiceProviderContactPersonVO;
import com.lgyun.system.user.vo.ServiceProviderInvoiceVO;
import com.lgyun.system.user.vo.admin.ServiceProviderDetailServiceProviderVO;
import com.lgyun.system.user.vo.admin.ServiceProviderListVO;
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

    private final IEnterpriseServiceProviderService enterpriseProviderService;
    private final IUserService userService;

    @Autowired
    @Lazy
    private IServiceProviderWorkerService serviceProviderWorkerService;

    @Autowired
    @Lazy
    private IAgreementService agreementService;

    @Override
    public Integer findCountByServiceProviderName(String serviceProviderName, Long serviceProviderId) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderEntity::getServiceProviderName, serviceProviderName)
                .ne(serviceProviderId != null, ServiceProviderEntity::getId, serviceProviderId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer findCountBySocialCreditNo(String socialCreditNo, Long serviceProviderId) {
        QueryWrapper<ServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderEntity::getSocialCreditNo, socialCreditNo)
                .ne(serviceProviderId != null, ServiceProviderEntity::getId, serviceProviderId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<EnterprisesVO>> getEnterpriseByServiceProvider(Query query, Long serviceProviderId, String keyword) {
        return enterpriseProviderService.getEnterpriseByServiceProvider(Condition.getPage(query.setDescs("create_time")), serviceProviderId, keyword);
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
        return R.data(page.setRecords(baseMapper.queryServiceProviderListAdmin(queryServiceProviderListDTO, page)));
    }

    @Override
    public R<ServiceProviderDetailServiceProviderVO> queryServiceProviderDetailServiceProvider(Long serviceProviderId) {
        return R.data(baseMapper.queryServiceProviderDetailServiceProvider(serviceProviderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createServiceProvider(AddServiceProviderDTO addServiceProviderDTO, AdminEntity adminEntity) {

        //判断服务商联系人是否已存在
        if (addServiceProviderDTO.getContact1Phone().equals(addServiceProviderDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }

        //判断服务商名称是否已存在
        Integer countByServiceProviderName = findCountByServiceProviderName(addServiceProviderDTO.getServiceProviderName(), null);
        if (countByServiceProviderName > 0) {
            return R.fail("名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = findCountBySocialCreditNo(addServiceProviderDTO.getSocialCreditNo(), null);
        if (countBySocialCreditNo > 0) {
            return R.fail("统一社会信用代码已存在");
        }

        //判断服务商联系人1是否已存在
        ServiceProviderWorkerEntity serviceProviderWorkerEntity = serviceProviderWorkerService.findByPhoneNumber(addServiceProviderDTO.getContact1Phone());
        if (serviceProviderWorkerEntity != null) {
            return R.fail("联系人1电话/手机：" + addServiceProviderDTO.getContact1Phone() + "已存在");
        }

        //判断服务商联系人2是否已存在
        serviceProviderWorkerEntity = serviceProviderWorkerService.findByPhoneNumber(addServiceProviderDTO.getContact2Phone());
        if (serviceProviderWorkerEntity != null) {
            return R.fail("联系人2电话/手机：" + addServiceProviderDTO.getContact2Phone() + "已存在");
        }

        ServiceProviderEntity serviceProviderEntity = new ServiceProviderEntity();
        BeanUtil.copy(addServiceProviderDTO, serviceProviderEntity);
        serviceProviderEntity.setCreateType(CreateType.PLATFORMCREATE);
        save(serviceProviderEntity);

        //新建联系人员工1
        User user1 = new User();
        user1.setUserType(UserType.SERVICEPROVIDER);
        user1.setAccount(addServiceProviderDTO.getContact1Phone());
        user1.setPhone(addServiceProviderDTO.getContact1Phone());
        userService.save(user1);

        serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
        serviceProviderWorkerEntity.setServiceProviderId(serviceProviderEntity.getId());
        serviceProviderWorkerEntity.setUserId(user1.getId());
        serviceProviderWorkerEntity.setWorkerName(addServiceProviderDTO.getContact1Name());
        serviceProviderWorkerEntity.setPositionName(addServiceProviderDTO.getContact1Position());
        serviceProviderWorkerEntity.setPhoneNumber(addServiceProviderDTO.getContact1Phone());
        serviceProviderWorkerEntity.setEmployeeUserName(addServiceProviderDTO.getContact1Phone());
        serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
        serviceProviderWorkerEntity.setAdminPower(true);
        serviceProviderWorkerService.save(serviceProviderWorkerEntity);

        //新建联系人员工2
        user1 = new User();
        user1.setUserType(UserType.SERVICEPROVIDER);
        user1.setAccount(addServiceProviderDTO.getContact2Phone());
        user1.setPhone(addServiceProviderDTO.getContact2Phone());
        userService.save(user1);

        serviceProviderWorkerEntity = new ServiceProviderWorkerEntity();
        serviceProviderWorkerEntity.setServiceProviderId(serviceProviderEntity.getId());
        serviceProviderWorkerEntity.setUserId(user1.getId());
        serviceProviderWorkerEntity.setWorkerName(addServiceProviderDTO.getContact2Name());
        serviceProviderWorkerEntity.setPositionName(addServiceProviderDTO.getContact2Position());
        serviceProviderWorkerEntity.setPhoneNumber(addServiceProviderDTO.getContact2Phone());
        serviceProviderWorkerEntity.setEmployeeUserName(addServiceProviderDTO.getContact2Phone());
        serviceProviderWorkerEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
        serviceProviderWorkerEntity.setAdminPower(true);
        serviceProviderWorkerService.save(serviceProviderWorkerEntity);

        //上传加盟合同
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.SERVICEPROVIDERJOINAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setPaperAgreementUrl(addServiceProviderDTO.getJoinContract());
        agreementEntity.setFirstSideSignPerson(adminEntity.getName());
        agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
        agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
        agreementService.save(agreementEntity);

        //上传服务商承诺函
        agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setPaperAgreementUrl(addServiceProviderDTO.getCommitmentLetter());
        agreementEntity.setFirstSideSignPerson(adminEntity.getName());
        agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
        agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
        agreementService.save(agreementEntity);

        return R.success("添加服务商成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateServiceProvider(UpdateServiceProviderDTO updateServiceProviderDTO, AdminEntity adminEntity) {

        ServiceProviderEntity serviceProviderEntity = getById(updateServiceProviderDTO.getServiceProviderId());
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        //判断商户名称是否已存在
        Integer countByServiceProviderName = findCountByServiceProviderName(updateServiceProviderDTO.getServiceProviderName(), serviceProviderEntity.getId());
        if (countByServiceProviderName > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = findCountBySocialCreditNo(updateServiceProviderDTO.getSocialCreditNo(), serviceProviderEntity.getId());
        if (countBySocialCreditNo > 0) {
            return R.fail("统一社会信用代码已存在");
        }

        //根据联系人生成商户员工
        AddOrUpdateServiceProviderContactDTO addOrUpdateServiceProviderContactDto = new AddOrUpdateServiceProviderContactDTO();
        addOrUpdateServiceProviderContactDto.setServiceProviderId(serviceProviderEntity.getId());
        addOrUpdateServiceProviderContactDto.setContact1Name(updateServiceProviderDTO.getContact1Name());
        addOrUpdateServiceProviderContactDto.setContact1Position(updateServiceProviderDTO.getContact1Position());
        addOrUpdateServiceProviderContactDto.setContact1Phone(updateServiceProviderDTO.getContact1Phone());
        addOrUpdateServiceProviderContactDto.setContact1Mail(updateServiceProviderDTO.getContact1Mail());
        addOrUpdateServiceProviderContactDto.setContact2Name(updateServiceProviderDTO.getContact2Name());
        addOrUpdateServiceProviderContactDto.setContact2Position(updateServiceProviderDTO.getContact2Position());
        addOrUpdateServiceProviderContactDto.setContact2Phone(updateServiceProviderDTO.getContact2Phone());
        addOrUpdateServiceProviderContactDto.setContact2Mail(updateServiceProviderDTO.getContact2Mail());
        R<String> result = serviceProviderWorkerService.addOrUpdateServiceProviderContact(addOrUpdateServiceProviderContactDto, null);
        if (!(result.isSuccess())) {
            return result;
        }

        //上传或修改加盟合同
        AgreementEntity agreementEntity = agreementService.findSuccessAgreement(null, serviceProviderEntity.getId(), AgreementType.ENTERPRISEJOINAGREEMENT, null, SignState.SIGNED);
        if (agreementEntity != null) {
            agreementEntity.setPaperAgreementUrl(updateServiceProviderDTO.getJoinContract());
            agreementService.updateById(agreementEntity);
        } else {
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setPaperAgreementUrl(updateServiceProviderDTO.getJoinContract());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
            agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
            agreementService.save(agreementEntity);
        }

        //上传或修改商户承诺函
        agreementEntity = agreementService.findSuccessAgreement(null, serviceProviderEntity.getId(), AgreementType.OTHERAGREEMENT, AuditState.APPROVED, null);
        if (agreementEntity != null) {
            agreementEntity.setPaperAgreementUrl(updateServiceProviderDTO.getCommitmentLetter());
            agreementService.updateById(agreementEntity);
        } else {
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setPaperAgreementUrl(updateServiceProviderDTO.getCommitmentLetter());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setServiceProviderId(serviceProviderEntity.getId());
            agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
            agreementService.save(agreementEntity);
        }

        BeanUtil.copy(updateServiceProviderDTO, serviceProviderEntity);
        updateById(serviceProviderEntity);

        return R.success("编辑商户成功");
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

}
