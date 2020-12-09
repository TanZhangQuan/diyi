package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.DigestUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.order.entity.AddressEntity;
import com.lgyun.system.order.feign.IOrderClient;
import com.lgyun.system.user.dto.ContactsInfoDTO;
import com.lgyun.system.user.dto.CreateEnterpriseDTO;
import com.lgyun.system.user.dto.EnterpriseListDTO;
import com.lgyun.system.user.dto.UpdateEnterpriseDTO;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.EnterpriseMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class EnterpriseServiceImpl extends BaseServiceImpl<EnterpriseMapper, EnterpriseEntity> implements IEnterpriseService {

    private IOrderClient orderClient;
    private IAgreementService agreementService;
    private IMakerEnterpriseService makerEnterpriseService;
    private IEnterpriseWorkerService enterpriseWorkerService;
    private IPartnerEnterpriseService partnerEnterpriseService;
    private IAgentMainEnterpriseService agentMainEnterpriseService;

    @Override
    public int queryCountById(Long enterpriseId) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);
        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseName(String enterpriseName) {
        return R.data(baseMapper.getEnterpriseName(enterpriseName));
    }

    @Override
    public R<MakerEnterpriseRelationVO> getEnterpriseId(Long enterpriseId, Long makerId) {
        int relevanceNum = makerEnterpriseService.queryMakerEnterpriseNum(enterpriseId, makerId, RelationshipType.RELEVANCE);
        int attentionNum = makerEnterpriseService.queryMakerEnterpriseNum(enterpriseId, makerId, RelationshipType.ATTENTION);
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);

        EnterpriseEntity enterpriseEntity = baseMapper.selectOne(queryWrapper);
        if (null == enterpriseEntity) {
            return R.fail("商户不存在");
        }

        MakerEnterpriseRelationVO makerEnterpriseRelationVO = BeanUtil.copy(enterpriseEntity, MakerEnterpriseRelationVO.class);
        if ((0 == relevanceNum && 0 == attentionNum)) {
            makerEnterpriseRelationVO.setContact1Phone(makerEnterpriseRelationVO.getContact1Phone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPersonName("***");
            makerEnterpriseRelationVO.setLegalPersonIdcard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position(null);
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.NORELATION);
            return R.data(makerEnterpriseRelationVO);
        } else if ((0 == relevanceNum && 0 < attentionNum)) {
            makerEnterpriseRelationVO.setContact1Phone(makerEnterpriseRelationVO.getContact1Phone().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            makerEnterpriseRelationVO.setBizLicenceUrl("*");
            makerEnterpriseRelationVO.setLegalPersonName("***");
            makerEnterpriseRelationVO.setLegalPersonIdcard("*********");
            makerEnterpriseRelationVO.setSocialCreditNo("*******");
            makerEnterpriseRelationVO.setContact1Position(null);
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.ATTENTION);
            return R.data(makerEnterpriseRelationVO);
        } else if (0 < relevanceNum && 0 == attentionNum) {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        } else {
            makerEnterpriseRelationVO.setRelationshipType(RelationshipType.RELEVANCE);
            return R.data(makerEnterpriseRelationVO);
        }
    }

    @Override
    public R<EnterpriseInfoVO> queryEnterpriseInfo(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseInfo(enterpriseId));
    }

    @Override
    public R<EnterprisesDetailVO> getEnterpriseDetailById(Long enterpriseId) {
        return R.data(baseMapper.getEnterpriseDetailById(enterpriseId));
    }

    @Override
    public R<IPage<EnterpriseListPaymentVO>> queryEnterpriseListPayment(String enterpriseName, IPage<EnterpriseListPaymentVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseListPayment(enterpriseName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createEnterprise(CreateEnterpriseDTO createEnterpriseDTO, Long agentMainId, Long partnerId) {

        //判断商户名称是否已存在
        int enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda().eq(EnterpriseEntity::getEnterpriseName, createEnterpriseDTO.getEnterpriseName()));
        if (enterpriseNum > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda().eq(EnterpriseEntity::getSocialCreditNo, createEnterpriseDTO.getSocialCreditNo()));
        if (enterpriseNum > 0) {
            return R.fail("商户统一社会信用代码已存在");
        }

        int serviceProviderWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda().eq(EnterpriseWorkerEntity::getEmployeeUserName, createEnterpriseDTO.getEmployeeUserName()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        serviceProviderWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda().eq(EnterpriseWorkerEntity::getPhoneNumber, createEnterpriseDTO.getPhoneNumber()));
        if (serviceProviderWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        EnterpriseEntity enterpriseEntity = new EnterpriseEntity();
        enterpriseEntity.setCreateType(CreateType.PLATFORMCREATE);
        enterpriseEntity.setInviteNo(createEnterpriseDTO.getPhoneNumber());
        BeanUtil.copy(createEnterpriseDTO, enterpriseEntity);
        save(enterpriseEntity);

        //保存收件地址
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setObjectId(enterpriseEntity.getId());
        addressEntity.setObjectType(ObjectType.ENTERPRISEPEOPLE);
        addressEntity.setBoolDefault(true);
        BeanUtils.copyProperties(createEnterpriseDTO, addressEntity);
        orderClient.createAddress(addressEntity);

        //密码加密
        createEnterpriseDTO.setEmployeePwd(DigestUtil.encrypt(createEnterpriseDTO.getEmployeePwd()));

        EnterpriseWorkerEntity enterpriseWorkerEntity = new EnterpriseWorkerEntity();
        enterpriseWorkerEntity.setPositionName(PositionName.MANAGEMENT);
        enterpriseWorkerEntity.setSuperAdmin(true);
        enterpriseWorkerEntity.setAdminPower(true);
        BeanUtil.copy(createEnterpriseDTO, enterpriseWorkerEntity);
        enterpriseWorkerEntity.setEnterpriseId(enterpriseEntity.getId());
        enterpriseWorkerService.save(enterpriseWorkerEntity);

        //创建渠道商-商户关联
        if (agentMainId != null) {
            AgentMainEnterpriseEntity agentMainEnterpriseEntity = new AgentMainEnterpriseEntity();
            agentMainEnterpriseEntity.setAgentMainId(agentMainId);
            agentMainEnterpriseEntity.setEnterpriseId(enterpriseEntity.getId());
            agentMainEnterpriseEntity.setCooperateType(CooperateType.CREATE);
            agentMainEnterpriseService.save(agentMainEnterpriseEntity);
        }

        //创建合伙人-商户关联
        if (partnerId != null) {
            PartnerEnterpriseEntity partnerEnterpriseEntity = new PartnerEnterpriseEntity();
            partnerEnterpriseEntity.setPartnerId(partnerId);
            partnerEnterpriseEntity.setEnterpriseId(enterpriseEntity.getId());
            partnerEnterpriseEntity.setCooperateType(CooperateType.CREATE);
            partnerEnterpriseService.save(partnerEnterpriseEntity);
        }

        return R.success("新建商户成功");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateEnterprise(UpdateEnterpriseDTO updateEnterpriseDTO, Long agentMainId, Long partnerId) {

        EnterpriseEntity enterpriseEntity = getById(updateEnterpriseDTO.getEnterpriseId());
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (agentMainId != null) {

            AgentMainEnterpriseEntity agentMainEnterpriseEntity = agentMainEnterpriseService.queryByAgentMainAndEnterprise(agentMainId, updateEnterpriseDTO.getEnterpriseId());
            if (agentMainEnterpriseEntity == null) {
                return R.fail("商户不属于渠道商");
            }

            if (!(CooperateType.CREATE.equals(agentMainEnterpriseEntity.getCooperateType()))) {
                return R.fail("非渠道商创建商户不可编辑");
            }
        }

        if (partnerId != null) {

            PartnerEnterpriseEntity partnerEnterpriseEntity = partnerEnterpriseService.queryByPartnerAndEnterprise(partnerId, updateEnterpriseDTO.getEnterpriseId());
            if (partnerEnterpriseEntity == null) {
                return R.fail("商户不属于合伙人");
            }

            if (!(CooperateType.CREATE.equals(partnerEnterpriseEntity.getCooperateType()))) {
                return R.fail("非合伙人创建商户不可编辑");
            }
        }

        //查询商户管理员
        EnterpriseWorkerEntity enterpriseWorkerEntity = enterpriseWorkerService.getOne(Wrappers.<EnterpriseWorkerEntity>query().lambda()
                .eq(EnterpriseWorkerEntity::getEnterpriseId, enterpriseEntity.getId())
                .isNull(EnterpriseWorkerEntity::getUpLevelId));

        if (enterpriseWorkerEntity == null) {
            return R.fail("商户管理员不存在");
        }

        if (StringUtils.isNotBlank(updateEnterpriseDTO.getEmployeePwd())) {
            if (updateEnterpriseDTO.getEmployeePwd().length() < 6 || updateEnterpriseDTO.getEmployeePwd().length() > 18) {
                return R.fail("请输入长度为6-18位的密码");
            }

            updateEnterpriseDTO.setEmployeePwd(DigestUtil.encrypt(updateEnterpriseDTO.getEmployeePwd()));
        } else {
            updateEnterpriseDTO.setEmployeePwd(enterpriseWorkerEntity.getEmployeePwd());
        }

        //判断商户名称是否已存在
        int enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda()
                .eq(EnterpriseEntity::getEnterpriseName, updateEnterpriseDTO.getEnterpriseName())
                .ne(EnterpriseEntity::getId, enterpriseEntity.getId()));
        if (enterpriseNum > 0) {
            return R.fail("商户名称已存在");
        }

        //判断社会信用代码是否已存在
        enterpriseNum = count(Wrappers.<EnterpriseEntity>query().lambda()
                .eq(EnterpriseEntity::getSocialCreditNo, updateEnterpriseDTO.getSocialCreditNo())
                .ne(EnterpriseEntity::getId, enterpriseEntity.getId()));
        if (enterpriseNum > 0) {
            return R.fail("商户统一社会信用代码已存在");
        }

        int enterpriseWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda()
                .eq(EnterpriseWorkerEntity::getEmployeeUserName, updateEnterpriseDTO.getEmployeeUserName())
                .ne(EnterpriseWorkerEntity::getId, enterpriseWorkerEntity.getId()));
        if (enterpriseWorkerNum > 0) {
            return R.fail("已存在相同用户名的管理员");
        }

        enterpriseWorkerNum = enterpriseWorkerService.count(Wrappers.<EnterpriseWorkerEntity>query().lambda()
                .eq(EnterpriseWorkerEntity::getPhoneNumber, updateEnterpriseDTO.getPhoneNumber())
                .ne(EnterpriseWorkerEntity::getId, enterpriseWorkerEntity.getId()));
        if (enterpriseWorkerNum > 0) {
            return R.fail("已存在相同手机号的管理员");
        }

        //上传加盟合同
        AgreementEntity agreementEntity = agreementService.getOne(Wrappers.<AgreementEntity>query().lambda()
                .eq(AgreementEntity::getEnterpriseId, enterpriseEntity.getId())
                .eq(AgreementEntity::getAgreementType, AgreementType.ENTERPRISEJOINAGREEMENT)
                .eq(AgreementEntity::getSignState, SignState.SIGNED)
                .eq(AgreementEntity::getAuditState, AuditState.APPROVED));

        if (agreementEntity == null) {
            return R.fail("商户加盟合同不存在");
        }

        //编辑商户员工
        BeanUtil.copy(updateEnterpriseDTO, enterpriseWorkerEntity);
        enterpriseWorkerService.updateById(enterpriseWorkerEntity);

        return R.success("编辑商户成功");
    }

    @Override
    public R<IPage<EnterpriseListAdminVO>> queryEnterpriseList(Long agentMainId, Long relBureauId, EnterpriseListDTO enterpriseListDTO, IPage<EnterpriseListAdminVO> page) {

        if (enterpriseListDTO.getBeginDate() != null && enterpriseListDTO.getEndDate() != null) {
            if (enterpriseListDTO.getBeginDate().after(enterpriseListDTO.getEndDate())) {
                return R.fail("开始时间不能大于结束时间");
            }
        }

        return R.data(page.setRecords(baseMapper.queryEnterpriseList(agentMainId, relBureauId, enterpriseListDTO, page)));
    }

    @Override
    public R<EnterpriseUpdateDetailVO> queryEnterpriseUpdateDetail(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseUpdateDetail(enterpriseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateEnterpriseState(Long enterpriseId, AccountState enterpriseState) {

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        if (!(enterpriseEntity.getEnterpriseState().equals(enterpriseState))) {
            enterpriseEntity.setEnterpriseState(enterpriseState);
            updateById(enterpriseEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R getEnterpriseAll(Long enterpriseId, String enterpriseName, IPage<EnterpriseEntity> page) {
        QueryWrapper<EnterpriseEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(enterpriseName) && null != enterpriseId) {
            queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId)
                    .like(EnterpriseEntity::getEnterpriseName, enterpriseName);
        }
        if (null != enterpriseId && StringUtils.isEmpty(enterpriseName)) {
            queryWrapper.lambda().eq(EnterpriseEntity::getId, enterpriseId);
        }
        if (StringUtils.isNotEmpty(enterpriseName) && null == enterpriseId) {
            queryWrapper.lambda().eq(EnterpriseEntity::getEnterpriseName, enterpriseName);
        }
        IPage<EnterpriseEntity> enterpriseEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(enterpriseEntityIPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateEnterpriseUrl(Long enterpriseId, String enterpriseUrl) {

        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        enterpriseEntity.setEnterpriseUrl(enterpriseUrl);
        updateById(enterpriseEntity);
        return R.success("编辑成功");
    }

    @Override
    public R<ContactInfoVO> queryContact(Long enterpriseId) {
        return R.data(baseMapper.queryContact(enterpriseId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> updateContact(Long enterpriseId, ContactsInfoDTO contactsInfoDTO) {
        EnterpriseEntity enterpriseEntity = getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        BeanUtil.copyProperties(contactsInfoDTO, enterpriseEntity);
        updateById(enterpriseEntity);
        return R.success("编辑联系人成功");
    }

    @Override
    public R<InvoiceVO> queryeInvoice(Long enterpriseId) {
        return R.data(baseMapper.queryeInvoice(enterpriseId));
    }

    @Override
    public R<IPage<EnterpriseIdNameListVO>> queryEnterpriseIdAndNameList(Long serviceProviderId, Long partnerId, String enterpriseName, IPage<EnterpriseIdNameListVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseIdAndNameList(serviceProviderId, partnerId, enterpriseName, page)));
    }

    @Override
    public R<EnterprisesDetailAgentMainVO> queryEnterpriseDetailAgentMain(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseDetailAgentMain(enterpriseId));
    }

    @Override
    public R<EnterprisesDetailPartnerVO> queryEnterpriseDetailPartner(Long enterpriseId) {
        return R.data(baseMapper.queryEnterpriseDetailPartner(enterpriseId));
    }

}
