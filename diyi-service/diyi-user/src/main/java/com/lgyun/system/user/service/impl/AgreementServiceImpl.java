package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.core.mp.support.Condition;
import com.lgyun.core.mp.support.Query;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Service 实现
 *
 * @author tzq
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AgreementServiceImpl extends BaseServiceImpl<AgreementMapper, AgreementEntity> implements IAgreementService {

    private final IOnlineAgreementTemplateService iOnlineAgreementTemplateService;
    private final IServiceProviderService serviceProviderService;
    private final IOnlineAgreementNeedSignService onlineAgreementNeedSignService;
    private final IEnterpriseServiceProviderService enterpriseServiceProviderService;

    private final IMakerEnterpriseService makerEnterpriseService;

    @Autowired
    @Lazy
    private IAgreementService agreementService;

    @Autowired
    @Lazy
    private IMakerService makerService;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Override
    public AgreementEntity findSuccessAgreement(Long enterpriseId, Long serviceProviderId, AgreementType agreementType, AuditState auditState, SignState signState) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(enterpriseId != null, AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(serviceProviderId != null, AgreementEntity::getServiceProviderId, serviceProviderId)
                .eq(AgreementEntity::getAgreementType, agreementType)
                .eq(auditState != null, AgreementEntity::getAuditState, auditState)
                .eq(signState != null, AgreementEntity::getSignState, signState);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<Map> makerIdFind(Long makerId, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, makerId)
                .eq(AgreementEntity::getSignType, SignType.PLATFORMAGREEMENT)
                .eq(AgreementEntity::getOnlineAgreementTemplateId, onlineAgreementTemplateId);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);

        Map map = new HashMap();
        if (null == agreementEntity) {
            OnlineAgreementTemplateEntity byId = iOnlineAgreementTemplateService.getById(onlineAgreementTemplateId);
            map.put("onlineAgreementTemplateId", onlineAgreementTemplateId);
            map.put("onlineAgreementNeedSignId", onlineAgreementNeedSignId);
            map.put("agreementTemplate", byId.getAgreementTemplate());
            map.put("signState", SignState.UNSIGN);
            return R.data(map);
        }
        map.put("onlineAgreementTemplateId", "");
        map.put("onlineAgreementNeedSignId", "");
        map.put("agreementTemplate", agreementEntity.getOnlineAgreementUrl());
        map.put("signState", agreementEntity.getSignState().getValue());
        return R.data(map);
    }

    @Override
    public List<AgreementEntity> findByEnterpriseId(Long enterpriseId,Long makerId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
        .eq(AgreementEntity::getAgreementType,AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT)
        .eq(AgreementEntity::getMakerId,makerId);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public AgreementWebVO findByEnterpriseAndType(Long enterpriseId, AgreementType agreementType, SignType signType) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        AgreementWebVO agreementWebVO = null;
        if (null != byId && null != agreementEntity) {
            agreementWebVO = BeanUtil.copy(agreementEntity, AgreementWebVO.class);
            agreementWebVO.setEnterpriseName(byId.getEnterpriseName());
        }
        return agreementWebVO;
    }

    @Override
    public R selectAuthorization(Long enterpriseId, IPage<AgreementEntity> page) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, AgreementType.ENTERPRISEPROMISE)
                .in(AgreementEntity::getSignType, SignType.PAPERAGREEMENT, SignType.PLATFORMAGREEMENT);
        IPage<AgreementEntity> agreementEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(agreementEntityIPage);
    }

    @Override
    public R<String> saveAuthorization(Long enterpriseId, String paperAgreementURL) {
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNING);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setPaperAgreementUrl(paperAgreementURL);
        agreementEntity.setFirstSideSignPerson("地衣众包平台");
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("上传成功");
    }

    @Override
    public IPage<AgreementWebVO> selectServiceAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, AgreementType agreementType) {
        return page.setRecords(baseMapper.selectServiceAgreement(enterpriseId, serviceProviderName, agreementNo, agreementType, page));
    }

    @Override
    public IPage<AgreementWebVO> selectServiceSupplementaryAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, AgreementType agreementType) {
        return page.setRecords(baseMapper.selectServiceSupplementaryAgreement(enterpriseId, serviceProviderName, agreementNo, agreementType, page));
    }

    @Override
    public R<String> saveSupplementaryAgreement(Long enterpriseId, String paperAgreementURL, Long serviceProviderId) {
        if (null == serviceProviderId || StringUtil.isBlank(paperAgreementURL)) {
            return R.fail("参数错误");
        }
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        if (null == serviceProviderEntity) {
            return R.fail("服务商不存在");
        }
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.SERENTSUPPLEMENTARYAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setServiceProviderId(serviceProviderId);
        agreementEntity.setPaperAgreementUrl(paperAgreementURL);
        agreementEntity.setFirstSideSignPerson(serviceProviderEntity.getServiceProviderName());
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("上传成功");
    }

    @Override
    public R<IPage<AgreementMakerWebVO>> selectMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId) {
        return R.data(page.setRecords(baseMapper.selectMakerAgreement(enterpriseId, page)));
    }

    @Override
    public R saveEnterpriseMakerAgreement(Long enterpriseId, String paperAgreementURL, String makerIds) {
        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在！");
        }
        List<Long> makerIdList = Func.toLongList(makerIds);
        List<String> error = new ArrayList<>();
        for (int i = 0; i < makerIdList.size(); i++) {
            MakerEntity makerEntity = makerService.getById(makerIdList.get(i));
            if (makerEntity != null) {
                AgreementEntity agreementEntity = new AgreementEntity();
                agreementEntity.setAgreementType(AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT);
                agreementEntity.setSignState(SignState.SIGNED);
                agreementEntity.setSignType(SignType.PAPERAGREEMENT);
                agreementEntity.setAuditState(AuditState.APPROVED);
                agreementEntity.setEnterpriseId(enterpriseId);
                agreementEntity.setPaperAgreementUrl(paperAgreementURL);
                agreementEntity.setSecondSideSignPerson(enterpriseEntity.getEnterpriseName());
                agreementEntity.setUploadDatetime(new Date());
                agreementEntity.setFirstSideSignPerson(makerEntity.getName());
                agreementEntity.setUploadPerson(enterpriseEntity.getEnterpriseName());
                agreementEntity.setMakerId(makerEntity.getId());
                this.saveOrUpdate(agreementEntity);
            } else {
                error.add("您选择的第" + (i + 1) + "个创客不存在，与他签署的《" + AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.getDesc() + "》失败！");
            }
        }
        if (error.size() > 0) {
            return R.data(error);
        }
        return R.success("上传成功");
    }

    @Override
    public R selectEnterpriseMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId) {
        return R.data(page.setRecords(baseMapper.selectEnterpriseMakerAgreement(enterpriseId, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R saveEntMakAgreement(Long enterpriseId, String paperAgreementURL, String makerIds, AgreementType agreementType) throws Exception {
        if (StringUtil.isBlank(makerIds)) {
            return R.fail("请选择创客");
        }
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        String[] split = makerIds.split(",");
        for (int i = 0; i < split.length; i++) {
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(agreementType);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setMakerId(Long.parseLong(split[i]));
            agreementEntity.setEnterpriseId(enterpriseId);
            agreementEntity.setPaperAgreementUrl(paperAgreementURL);
            agreementEntity.setUploadDatetime(new Date());
            agreementEntity.setUploadPerson(byId.getEnterpriseName());
            agreementEntity.setFirstSideSignPerson(byId.getEnterpriseName());
            MakerEntity makerEntity = makerService.getById(Long.parseLong(split[i]));
            agreementEntity.setSecondSideSignPerson(makerEntity.getName());
            agreementService.save(agreementEntity);
        }
        return R.success("上传成功");
    }

    @Override
    public R findSeriveAgreement(String agreementNo, Long serviceProviderId) {
        return R.data(baseMapper.findSeriveAgreement(agreementNo, serviceProviderId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadSupplement(String contractUrl, Long serviceProviderId, Long enterpriseId) {

        if (StringUtil.isBlank(contractUrl)) {
            return R.fail("服务商和商户的补充协议为空");
        }
        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.SERENTSUPPLEMENTARYAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setServiceProviderId(serviceProviderId);
        agreementEntity.setPaperAgreementUrl(contractUrl);
        agreementEntity.setFirstSideSignPerson(serviceProviderEntity.getServiceProviderName());
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("操作成功");
    }

    @Override
    public R findMakerAgreement(String agreementNo, Long serviceProviderId, String makerName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findMakerAgreement(agreementNo, serviceProviderId, makerName, page)));
    }

    @Override
    public R findEnterpriseAgreement(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseAgreement(agreementNo, serviceProviderId, enterpriseName, page)));
    }

    @Override
    public R findEnterprisePromise(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterprisePromise(agreementNo, serviceProviderId, enterpriseName, page)));
    }

    @Override
    public R findEnterpriseSupplement(String agreementNo, Long serviceProviderId, String enterpriseName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseSupplement(agreementNo, serviceProviderId, enterpriseName, page)));
    }

    @Override
    public R<AgreementEntity> findAdminMakerId(Long makerId, AgreementType agreementType) {
        if (!(AgreementType.MAKERJOINAGREEMENT.equals(agreementType) || AgreementType.MAKERPOWERATTORNEY.equals(agreementType))) {
            return R.fail("合同协议类别错误");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getMakerId, makerId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return R.data(agreementEntity);
    }

    @Override
    public R findAdMaEnterAgreement(Long makerId, String enterpriseName, IPage<AgreementMakerEnterAdminVO> page) {
        return R.data(page.setRecords(baseMapper.findAdMaEnterAgreement(makerId, enterpriseName, page)));
    }

    @Override
    public R saveAdminAgreement(Long makerId, Long enterpriseId, Long serviceProviderId, Long objectId, ObjectType objectType, AgreementType agreementType, String paperAgreementUrl) {
        AgreementEntity agreementEntity = null;
        if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType) || AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType) || AgreementType.ENTERPRISEPROMISE.equals(agreementType)) {
            agreementEntity = new AgreementEntity();
        } else {
            if (ObjectType.MAKERPEOPLE.equals(objectType)) {
                QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(AgreementEntity::getMakerId, objectId)
                        .eq(AgreementEntity::getAgreementType, agreementType);
                agreementEntity = baseMapper.selectOne(queryWrapper);

                if (agreementEntity == null) {
                    agreementEntity = new AgreementEntity();
                }

                OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = iOnlineAgreementTemplateService.findTemplateType(agreementType,0);
                if (null != onlineAgreementTemplateEntity) {
                    agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
                }
            }

            if (ObjectType.SERVICEPEOPLE.equals(objectType)) {
                QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(AgreementEntity::getServiceProviderId, objectId)
                        .eq(AgreementEntity::getAgreementType, agreementType);
                agreementEntity = baseMapper.selectOne(queryWrapper);
            }

            if (ObjectType.ENTERPRISEPEOPLE.equals(objectType)) {
                QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, objectId)
                        .eq(AgreementEntity::getAgreementType, agreementType);
                agreementEntity = baseMapper.selectOne(queryWrapper);
            }

        }

        if (null == agreementEntity) {
            agreementEntity = new AgreementEntity();
        }

        agreementEntity.setAgreementType(agreementType);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        if (ObjectType.MAKERPEOPLE.equals(objectType)) {
            agreementEntity.setMakerId(objectId);
            if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setEnterpriseId(enterpriseId);
            }
        } else if (ObjectType.ENTERPRISEPEOPLE.equals(objectType)) {
            agreementEntity.setEnterpriseId(objectId);
            if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setMakerId(makerId);
            }
            if (AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setServiceProviderId(serviceProviderId);
            }
        } else if (ObjectType.SERVICEPEOPLE.equals(objectType)) {
            agreementEntity.setServiceProviderId(objectId);
            if (AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setEnterpriseId(enterpriseId);
            }
        } else if (ObjectType.CHANNELPEOPLE.equals(objectType)) {
            agreementEntity.setAgentMainId(objectId);
        } else if (ObjectType.RELEVANTPEOPLE.equals(objectType)) {
            agreementEntity.setRelBureauId(objectId);
        } else if (ObjectType.PARTNERSHIPPEOPLE.equals(objectType)) {
            agreementEntity.setPartnerId(objectId);
        }
        agreementEntity.setPaperAgreementUrl(paperAgreementUrl);
        if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
            agreementEntity.setFirstSideSignPerson("");
            agreementEntity.setSecondSideSignPerson("");
        } else if (AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
            agreementEntity.setFirstSideSignPerson("地衣众包平台");
            agreementEntity.setSecondSideSignPerson("");
        } else {
            agreementEntity.setFirstSideSignPerson("地衣众包平台");
            agreementEntity.setSecondSideSignPerson("");
        }
        agreementEntity.setSignState(SignState.SIGNED);
        saveOrUpdate(agreementEntity);
        if(AgreementType.MAKERJOINAGREEMENT.equals(agreementType) || AgreementType.MAKERPOWERATTORNEY.equals(agreementType)){
            MakerEntity makerServiceById = makerService.getById(objectId);
            if(AgreementType.MAKERJOINAGREEMENT.equals(agreementType)){
                makerServiceById.setEmpowerSignState(SignState.SIGNED);
            }
            if(AgreementType.MAKERPOWERATTORNEY.equals(agreementType)){
                makerServiceById.setJoinSignState(SignState.SIGNED);
            }
            makerService.saveOrUpdate(makerServiceById);
        }

        if(AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)){
            if(ObjectType.MAKERPEOPLE.equals(objectType)){
                makerEnterpriseService.makerEnterpriseEntitySave(enterpriseId,objectId);
            }
            if(ObjectType.ENTERPRISEPEOPLE.equals(objectType)){
                makerEnterpriseService.makerEnterpriseEntitySave(objectId,makerId);
            }
        }

        return R.success("操作成功");
    }

    @Override
    public R queryAdminEnterpriseId(Long enterpriseId, AgreementType agreementType) {
        if (!(AgreementType.ENTERPRISEJOINAGREEMENT.equals(agreementType) || AgreementType.ENTERPRISEPOWERATTORNEY.equals(agreementType) || AgreementType.ENTERPRISEPRICEAGREEMENT.equals(agreementType))) {
            return R.fail("合同协议类别错误!!!");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return R.data(agreementEntity);
    }

    @Override
    public R findEnterIdServiceAgreement(Long enterpriseId, String serviceProviderName, IPage<AgreementEnterServiceAdminVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterIdServiceAgreement(enterpriseId, serviceProviderName, page)));
    }

    @Override
    public R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType) {
        if (!(AgreementType.SERVICEPROVIDERJOINAGREEMENT.equals(agreementType))) {
            return R.fail("合同协议类别错误!!!");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getServiceProviderId, serviceProviderId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return R.data(agreementEntity);
    }

    @Override
    public R getRelationServiceProvider(Query query, Long enterpriseId, String keyWord) {
        return enterpriseServiceProviderService.getServiceProvidersByEnterpriseId(enterpriseId, keyWord, Condition.getPage(query.setDescs("t1.create_time")));
    }

    @Override
    public R queryOnlineAgreementUrl(Long agreementId) {
        AgreementEntity agreementEntity = agreementService.getById(agreementId);
        if (null == agreementEntity) {
            return R.fail("合同不存在");
        }

        return R.data(agreementEntity.getOnlineAgreementUrl());
    }

    @Override
    public R queryMakerAgreementState(String makerName, IPage<AgreementMakerStateAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerAgreementState(makerName, page)));
    }

    @Override
    public R queryMakerIdSupplement(Long makerId, IPage<AgreementMakerEnterAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerIdSupplement(makerId, null, page)));
    }

    @Override
    public R queryEnterpriseAgreementState(String enterpriseName, IPage<AgreementEnterpriseStateAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseAgreementState(enterpriseName, page)));
    }

    @Override
    public R queryEnterpriseIdSupplement(Long enterpriseId, IPage<AgreementMakerEnterAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerIdSupplement(null, enterpriseId, page)));
    }

    @Override
    public R queryEnterIdServiceSupplement(Long enterpriseId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterIdServiceSupplement(enterpriseId, null, page)));
    }

    @Override
    public R queryEnterIdPromise(Long enterpriseId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterIdPromise(enterpriseId, page)));
    }

    @Override
    public R queryServiceAgreementState(String serviceProviderName, IPage<AgreementServiceStateAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceAgreementState(serviceProviderName, page)));
    }

    @Override
    public R queryServiceIdEnterSupplement(Long serviceProviderId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterIdServiceSupplement(null, serviceProviderId, page)));
    }

    @Override
    public R saveAdminAgreementId(Long agreementId, String agreementUrl) {
        AgreementEntity agreementEntity = getById(agreementId);
        if (null == agreementEntity) {
            return R.fail("合同不存在");
        }
        agreementEntity.setPaperAgreementUrl(agreementUrl);
        saveOrUpdate(agreementEntity);
        return R.success("编辑成功");
    }

    @Override
    public R queryAdminMakerAll(Long makerId, String makerName, IPage<MakerEntity> page) {
        return makerService.getMakerAll(makerId, makerName, page);
    }

    @Override
    public R queryAdminEnterpriseAll(Long enterpriseId, String enterpriseName, IPage<EnterpriseEntity> page) {
        return enterpriseService.getEnterpriseAll(enterpriseId, enterpriseName, page);
    }

    @Override
    public R queryAdminServiceAll(Long serviceProviderId, String serviceProviderName, IPage<ServiceProviderEntity> page) {
        return serviceProviderService.getServiceAll(serviceProviderId, serviceProviderName, page);
    }

    @Override
    public void deleteByEnterprise(Long enterpriseId, AgreementType agreementType) {
        baseMapper.deleteByEnterprise(enterpriseId, agreementType);
    }

    @Override
    public int queryEntMakSupplementaryAgreementNum(Long makerId, Long enterpriseId) {

        return count(Wrappers.<AgreementEntity>query().lambda().eq(AgreementEntity::getMakerId, makerId)
                .eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT)
                .eq(AgreementEntity::getSignState, SignState.SIGNED)
                .eq(AgreementEntity::getAuditState, AuditState.APPROVED));
    }

}
