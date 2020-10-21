package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.dto.admin.AddAdminAgentMainDTO;
import com.lgyun.system.user.dto.admin.AddOrUpdateAgentMainContactDTO;
import com.lgyun.system.user.dto.admin.QueryAgentMainDTO;
import com.lgyun.system.user.dto.admin.UpdateAgentMainDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.AgentMainMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.admin.AdminAgentMainServiceProviderListVO;
import com.lgyun.system.user.vo.admin.AdminAgentMainVO;
import com.lgyun.system.user.vo.admin.AgentMainTransactionVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 渠道商信息表 Service 实现
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentMainServiceImpl extends BaseServiceImpl<AgentMainMapper, AgentMainEntity> implements IAgentMainService {

    private IAgentPersonService agentPersonService;
    private IUserService userService;
    private IAgreementService agreementService;
    private IAgentProviderService agentProviderService;
    private IServiceProviderMakerService serviceProviderMakerService;

    @Override
    public Integer findCountByEnterpriseName(String enterpriseName, Long enterpriseId) {
        QueryWrapper<AgentMainEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainEntity::getEnterpriseName, enterpriseName)
                .ne(enterpriseId != null, AgentMainEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public Integer findCountBySocialCreditNo(String socialCreditNo, Long enterpriseId) {
        QueryWrapper<AgentMainEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainEntity::getSocialCreditNo, socialCreditNo)
                .ne(enterpriseId != null, AgentMainEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        return null;
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        return null;
    }

    @Override
    public R<IPage<AdminAgentMainVO>> getAgentMainList(IPage<AdminAgentMainVO> page, QueryAgentMainDTO queryAgentMainDTO) {
        if (queryAgentMainDTO.getBeginDate() != null && queryAgentMainDTO.getEndDate() != null) {
            if (queryAgentMainDTO.getBeginDate().after(queryAgentMainDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }
        return R.data(page.setRecords(baseMapper.getAgentMainList(queryAgentMainDTO, page)));
    }

    @Override
    public R updateIllegal(Long agentMainId, AdminEntity adminEntity) {
        return R.status(updateAgentState(agentMainId, AccountState.ILLEGAL, adminEntity.getUserId()));
    }

    @Override
    public R updateFreeze(Long agentMainId, AdminEntity adminEntity) {
        return R.status(updateAgentState(agentMainId, AccountState.FREEZE, adminEntity.getUserId()));
    }

    @Override
    public R updateNormal(Long agentMainId, AdminEntity adminEntity) {
        return R.status(updateAgentState(agentMainId, AccountState.NORMAL, adminEntity.getUserId()));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R createAgentMain(AddAdminAgentMainDTO addAdminAgentMainDTO, AdminEntity adminEntity) {
        //判断渠道商联系人是否相同
        if (addAdminAgentMainDTO.getContact1Phone().equals(addAdminAgentMainDTO.getContact2Phone())) {
            return R.fail("联系人1电话/手机和联系人2电话/手机不能一致");
        }
        //判断渠道商名称是否已存在
        Integer countByEnterpriseName = findCountByEnterpriseName(addAdminAgentMainDTO.getEnterpriseName(), null);
        if (countByEnterpriseName != null) {
            return R.fail("名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = findCountBySocialCreditNo(addAdminAgentMainDTO.getSocialCreditNo(), null);
        if (countBySocialCreditNo != null) {
            return R.fail("统一社会信用代码已存在");
        }

        //判断渠道商联系人1是否已存在
        Integer countByPhoneNumber1 = agentPersonService.findCountByPhoneNumber(addAdminAgentMainDTO.getContact1Phone());
        if (countByPhoneNumber1 > 0) {
            return R.fail("联系人1电话/手机：" + addAdminAgentMainDTO.getContact1Phone() + "已存在");
        }

        //判断渠道商联系人2是否已存在
        Integer countByPhoneNumber2 = agentPersonService.findCountByPhoneNumber(addAdminAgentMainDTO.getContact2Phone());
        if (countByPhoneNumber2 > 0) {
            return R.fail("联系人2电话/手机：" + addAdminAgentMainDTO.getContact2Phone() + "已存在");
        }
        AgentMainEntity agentMainEntity = new AgentMainEntity();
        BeanUtil.copy(addAdminAgentMainDTO, agentMainEntity);
        agentMainEntity.setCreateType(CreateType.PLATFORMCREATE);
        save(agentMainEntity);

        //新建联系人员工1
        User user = new User();
        user.setUserType(UserType.AGENTMAIN);
        user.setAccount(addAdminAgentMainDTO.getContact1Phone());
        user.setPhone(addAdminAgentMainDTO.getContact1Phone());
        userService.save(user);

        AgentPersonEntity agentPersonEntity = new AgentPersonEntity();
        agentPersonEntity.setAgentMainId(agentMainEntity.getId());
        agentPersonEntity.setWorkerId(user.getId());
        agentPersonEntity.setWorkerName(addAdminAgentMainDTO.getContact1Name());
        agentPersonEntity.setPositionName(addAdminAgentMainDTO.getContact1Position());
        agentPersonEntity.setPhoneNumber(addAdminAgentMainDTO.getContact1Phone());
        agentPersonEntity.setEmployeeUserName(addAdminAgentMainDTO.getContact1Phone());
        agentPersonEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
        agentPersonEntity.setAdminPower(true);
        agentPersonService.save(agentPersonEntity);

        //新建联系人员工2
        user = new User();
        user.setUserType(UserType.AGENTMAIN);
        user.setAccount(addAdminAgentMainDTO.getContact2Phone());
        user.setPhone(addAdminAgentMainDTO.getContact2Phone());
        userService.save(user);

        agentPersonEntity = new AgentPersonEntity();
        agentPersonEntity.setAgentMainId(agentMainEntity.getId());
        agentPersonEntity.setWorkerId(user.getId());
        agentPersonEntity.setWorkerName(addAdminAgentMainDTO.getContact2Name());
        agentPersonEntity.setPositionName(addAdminAgentMainDTO.getContact2Position());
        agentPersonEntity.setPhoneNumber(addAdminAgentMainDTO.getContact2Phone());
        agentPersonEntity.setEmployeeUserName(addAdminAgentMainDTO.getContact2Phone());
        agentPersonEntity.setEmployeePwd(DigestUtil.encrypt("123456"));
        agentPersonEntity.setAdminPower(true);
        agentPersonService.save(agentPersonEntity);

        //上传加盟合同
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.AGENTJOINAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setPaperAgreementUrl(addAdminAgentMainDTO.getJoinContract());
        agreementEntity.setFirstSideSignPerson(adminEntity.getName());
        agreementEntity.setAgentId(agentMainEntity.getId());
        agreementEntity.setSecondSideSignPerson(agentMainEntity.getContact1Name());
        agreementService.save(agreementEntity);

        //上传商户承诺函
        agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setPaperAgreementUrl(addAdminAgentMainDTO.getCommitmentLetter());
        agreementEntity.setFirstSideSignPerson(adminEntity.getName());
        agreementEntity.setAgentId(agentMainEntity.getId());
        agreementEntity.setSecondSideSignPerson(agentMainEntity.getContact1Name());
        agreementService.save(agreementEntity);

        return R.success("添加渠道商成功");
    }

    @Override
    public R updateAgentMain(UpdateAgentMainDTO updateAgentMainDTO, AdminEntity adminEntity) {
        AgentMainEntity agentMainEntity = getById(updateAgentMainDTO.getAgentMainId());
        if (agentMainEntity == null) {
            return R.fail("商户不存在");
        }

        //判断商户名称是否已存在
        Integer countByEnterpriseName = findCountByEnterpriseName(updateAgentMainDTO.getEnterpriseName(), agentMainEntity.getId());
        if (countByEnterpriseName > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        Integer countBySocialCreditNo = findCountBySocialCreditNo(updateAgentMainDTO.getSocialCreditNo(), agentMainEntity.getId());
        if (countBySocialCreditNo > 0) {
            return R.fail("统一社会信用代码已存在");
        }
        //根据联系人生成商户员工
        AddOrUpdateAgentMainContactDTO addOrUpdateAgentMainContactDto = new AddOrUpdateAgentMainContactDTO();
        addOrUpdateAgentMainContactDto.setEnterpriseId(agentMainEntity.getId());
        addOrUpdateAgentMainContactDto.setContact1Name(updateAgentMainDTO.getContact1Name());
        addOrUpdateAgentMainContactDto.setContact1Position(updateAgentMainDTO.getContact1Position());
        addOrUpdateAgentMainContactDto.setContact1Phone(updateAgentMainDTO.getContact1Phone());
        addOrUpdateAgentMainContactDto.setContact1Mail(updateAgentMainDTO.getContact1Mail());
        addOrUpdateAgentMainContactDto.setContact2Name(updateAgentMainDTO.getContact2Name());
        addOrUpdateAgentMainContactDto.setContact2Position(updateAgentMainDTO.getContact2Position());
        addOrUpdateAgentMainContactDto.setContact2Phone(updateAgentMainDTO.getContact2Phone());
        addOrUpdateAgentMainContactDto.setContact2Mail(updateAgentMainDTO.getContact2Mail());
        R<String> result = agentPersonService.addOrUpdateEnterpriseContact(addOrUpdateAgentMainContactDto, null);
        if (!(result.isSuccess())) {
            return result;
        }
        //上传或修改加盟合同
        AgreementEntity agreementEntity = agreementService.findSuccessAgreement(agentMainEntity.getId(), null, AgreementType.AGENTJOINAGREEMENT, null, SignState.SIGNED);
        if (agreementEntity != null) {
            agreementEntity.setPaperAgreementUrl(updateAgentMainDTO.getJoinContract());
            agreementService.updateById(agreementEntity);
        } else {
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.AGENTJOINAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setPaperAgreementUrl(updateAgentMainDTO.getJoinContract());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setEnterpriseId(agentMainEntity.getId());
            agreementEntity.setSecondSideSignPerson(agentMainEntity.getContact1Name());
            agreementService.save(agreementEntity);
        }

        //上传或修改商户承诺函
        agreementEntity = agreementService.findSuccessAgreement(agentMainEntity.getId(), null, AgreementType.OTHERAGREEMENT, AuditState.APPROVED, null);
        if (agreementEntity != null) {
            agreementEntity.setPaperAgreementUrl(updateAgentMainDTO.getCommitmentLetter());
            agreementService.updateById(agreementEntity);
        } else {
            agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setPaperAgreementUrl(updateAgentMainDTO.getCommitmentLetter());
            agreementEntity.setFirstSideSignPerson(adminEntity.getName());
            agreementEntity.setEnterpriseId(agentMainEntity.getId());
            agreementEntity.setSecondSideSignPerson(agentMainEntity.getContact1Name());
            agreementService.save(agreementEntity);
        }
        BeanUtil.copy(updateAgentMainDTO, agentMainEntity);
        updateById(agentMainEntity);
        return R.success("编辑商户成功");
    }

    @Override
    public R<AgentMainTransactionVO> transactionByAgentMainId(Long agentMainId) {
        AgentMainEntity agentMainEntity = getById(agentMainId);

        if (agentMainEntity == null) {
            return R.fail("商户不存在");
        }
        AgentMainTransactionVO agentMainTransactionVO = baseMapper.getTransactionByAgentMainId(agentMainId);
        return R.data(agentMainTransactionVO);
    }

    @Override
    public R<IPage<AdminAgentMainServiceProviderListVO>> getCooperativeServiceProvider(Long agentMainId, Query query) {




        return null;
    }

    @Override
    public R updateOpenAgentProvider(Long agentProviderId, AdminEntity adminEntity) {

        return null;
    }

    @Override
    public R updateCloseAgentProvider(Long agentProviderId, AdminEntity adminEntity) {
        return null;
    }

    @Override
    public R updateRevokeAgentProvider(Long agentProviderId, AdminEntity adminEntity) {
        return null;
    }


    public Boolean updateAgentState(Long agentMainId, AccountState accountState, Long updateUser) {
        AgentMainEntity agentMainEntity = baseMapper.selectById(agentMainId);
        agentMainEntity.setUpdateUser(updateUser);
        agentMainEntity.setUpdateTime(new Date());
        if (AccountState.NORMAL.equals(accountState)) {
            agentMainEntity.setAgentState(AccountState.NORMAL);
        } else if (AccountState.FREEZE.equals(accountState)) {
            agentMainEntity.setAgentState(AccountState.FREEZE);
        } else {
            agentMainEntity.setAgentState(AccountState.ILLEGAL);
        }
        return this.updateById(agentMainEntity);
    }

}
