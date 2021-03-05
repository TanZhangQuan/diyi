package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.constant.BladeConstant;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.Func;
import com.lgyun.common.tool.SnowflakeIdWorker;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
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

    private final IMakerEnterpriseService makerEnterpriseService;
    private final IOnlineAgreementTemplateService iOnlineAgreementTemplateService;

    @Autowired
    @Lazy
    private IMakerService makerService;

    @Autowired
    @Lazy
    private IAgreementService agreementService;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;

    @Autowired
    @Lazy
    private IServiceProviderService serviceProviderService;

    @Override
    public R<Map> makerIdFind(Long makerId, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getSignType, SignType.PLATFORMAGREEMENT)
                .eq(AgreementEntity::getOnlineAgreementTemplateId, onlineAgreementTemplateId);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);

        Map map = new HashMap();
        if (null == agreementEntity) {
            OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = iOnlineAgreementTemplateService.getById(onlineAgreementTemplateId);
            map.put("onlineAgreementTemplateId", onlineAgreementTemplateId);
            map.put("onlineAgreementNeedSignId", onlineAgreementNeedSignId);
            map.put("agreementTemplate", onlineAgreementTemplateEntity.getAgreementTemplate());
            map.put("signState", SignState.UNSIGN);
            return R.data(map);
        }
        map.put("onlineAgreementTemplateId", "");
        map.put("onlineAgreementNeedSignId", "");
        map.put("agreementTemplate", agreementEntity.getAgreementUrl());
        map.put("signState", agreementEntity.getSignState().getValue());
        return R.data(map);
    }

    @Override
    public List<AgreementEntity> findByEnterpriseId(Long enterpriseId, Long makerId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyA, ObjectType.ENTERPRISEPEOPLE).eq(AgreementEntity::getPartyAId, enterpriseId)
                .eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getAgreementType, AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public AgreementWebVO findByEnterpriseAndType(Long enterpriseId, AgreementType agreementType, SignType signType) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.ENTERPRISEPEOPLE).eq(AgreementEntity::getPartyBId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        AgreementWebVO agreementWebVO = null;
        if (null != enterpriseEntity && null != agreementEntity) {
            agreementWebVO = BeanUtil.copy(agreementEntity, AgreementWebVO.class);
            agreementWebVO.setEnterpriseName(enterpriseEntity.getEnterpriseName());
        }
        return agreementWebVO;
    }

    @Override
    public R selectAuthorization(Long enterpriseId, IPage<AgreementEntity> page) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.ENTERPRISEPEOPLE).eq(AgreementEntity::getPartyBId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, AgreementType.ENTERPRISEPROMISE)
                .in(AgreementEntity::getSignType, SignType.PAPERAGREEMENT, SignType.PLATFORMAGREEMENT);
        IPage<AgreementEntity> agreementEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(agreementEntityIPage);
    }

    @Override
    public R<String> saveAuthorization(Long enterpriseId, String agreementUrl) {
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.ENTERPRISEPROMISE);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setValidState(ValidState.VALIDING);
        agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
        agreementEntity.setPartyB(ObjectType.ENTERPRISEPEOPLE);
        agreementEntity.setPartyBId(enterpriseId);
        agreementEntity.setAgreementUrl(agreementUrl);
        save(agreementEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<IPage<AgreementWebVO>> queryServiceProviderAgreement(Long enterpriseId, AgreementType agreementType, String serviceProviderName, String agreementNo, IPage<AgreementWebVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderAgreement(enterpriseId, agreementType, serviceProviderName, agreementNo, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveSupplementaryAgreement(Long enterpriseId, String agreementUrl, Long serviceProviderId) {
        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        if (null == serviceProviderEntity) {
            return R.fail("服务商不存在");
        }
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.SERENTSUPPLEMENTARYAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setValidState(ValidState.VALIDING);
        agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
        agreementEntity.setPartyA(ObjectType.SERVICEPEOPLE);
        agreementEntity.setPartyAId(serviceProviderId);
        agreementEntity.setPartyB(ObjectType.ENTERPRISEPEOPLE);
        agreementEntity.setPartyBId(enterpriseId);
        agreementEntity.setAgreementUrl(agreementUrl);
        save(agreementEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<IPage<AgreementMakerWebVO>> queryMakerAgreement(Long enterpriseId, IPage<AgreementMakerWebVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerAgreement(enterpriseId, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveEnterpriseMakerAgreement(Long enterpriseId, String agreementUrl, String makerIds) {
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
                agreementEntity.setSignType(SignType.PAPERAGREEMENT);
                agreementEntity.setAuditState(AuditState.APPROVED);
                agreementEntity.setSignState(SignState.SIGNED);
                agreementEntity.setValidState(ValidState.VALIDING);
                agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
                agreementEntity.setAgreementUrl(agreementUrl);
                agreementEntity.setPartyA(ObjectType.ENTERPRISEPEOPLE);
                agreementEntity.setPartyAId(enterpriseId);
                agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
                agreementEntity.setPartyBId(makerEntity.getId());
                saveOrUpdate(agreementEntity);
            } else {
                error.add("您选择的第" + (i + 1) + "个创客不存在，与他签署的《" + AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.getDesc() + "》失败！");
            }
        }
        if (error.size() > 0) {
            return R.data(error.toString());
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R<IPage<EnterpriseMakerAgreementVO>> queryEnterpriseMakerSupplementaryAgreement(Long enterpriseId, IPage<EnterpriseMakerAgreementVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseMakerSupplementaryAgreement(enterpriseId, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveEntMakAgreement(Long enterpriseId, String agreementUrl, String makerIds, AgreementType agreementType) {
        if (StringUtil.isBlank(makerIds)) {
            return R.fail("请选择创客");
        }

        String[] split = makerIds.split(",");
        for (int i = 0; i < split.length; i++) {
            AgreementEntity agreementEntity = new AgreementEntity();
            agreementEntity.setAgreementType(agreementType);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setAuditState(AuditState.APPROVED);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setValidState(ValidState.VALIDING);
            agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
            agreementEntity.setPartyA(ObjectType.ENTERPRISEPEOPLE);
            agreementEntity.setPartyAId(enterpriseId);
            agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
            agreementEntity.setPartyBId(Long.parseLong(split[i]));
            agreementEntity.setAgreementUrl(agreementUrl);
            agreementService.save(agreementEntity);
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R findSeriveAgreement(Long serviceProviderId, String agreementNo) {
        return R.data(baseMapper.findSeriveAgreement(serviceProviderId, agreementNo));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R uploadSupplement(String contractUrl, Long serviceProviderId, Long enterpriseId) {
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.SERENTSUPPLEMENTARYAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setValidState(ValidState.VALIDING);
        agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());
        agreementEntity.setPartyA(ObjectType.SERVICEPEOPLE);
        agreementEntity.setPartyAId(serviceProviderId);
        agreementEntity.setPartyB(ObjectType.ENTERPRISEPEOPLE);
        agreementEntity.setPartyBId(enterpriseId);
        agreementEntity.setAgreementUrl(contractUrl);
        save(agreementEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R findMakerAgreement(Long serviceProviderId, String agreementNo, String makerName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findMakerAgreement(serviceProviderId, agreementNo, makerName, page)));
    }

    @Override
    public R findEnterpriseAgreement(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseAgreement(serviceProviderId, agreementNo, enterpriseName, page)));
    }

    @Override
    public R findEnterprisePromise(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterprisePromise(serviceProviderId, agreementNo, enterpriseName, page)));
    }

    @Override
    public R findEnterpriseSupplement(Long serviceProviderId, String agreementNo, String enterpriseName, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findEnterpriseSupplement(serviceProviderId, agreementNo, enterpriseName, page)));
    }

    @Override
    public R<AgreementEntity> findAdminMakerId(Long makerId, AgreementType agreementType) {
        if (!(AgreementType.MAKERJOINAGREEMENT.equals(agreementType) || AgreementType.MAKERPOWERATTORNEY.equals(agreementType))) {
            return R.fail("合同协议类别错误");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return R.data(agreementEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveAdminAgreement(Long makerId, Long enterpriseId, Long serviceProviderId, Long objectId, ObjectType objectType, AgreementType agreementType, String agreementUrl) {
        AgreementEntity agreementEntity = null;
        if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType) || AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType) || AgreementType.ENTERPRISEPROMISE.equals(agreementType)) {
            agreementEntity = new AgreementEntity();
        } else {
            if (ObjectType.MAKERPEOPLE.equals(objectType)) {
                QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, objectId)
                        .eq(AgreementEntity::getAgreementType, agreementType);
                agreementEntity = baseMapper.selectOne(queryWrapper);

                if (agreementEntity == null) {
                    agreementEntity = new AgreementEntity();
                }

                OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = iOnlineAgreementTemplateService.findTemplateType(agreementType, true);
                if (null != onlineAgreementTemplateEntity) {
                    agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
                }
            }

            if (ObjectType.SERVICEPEOPLE.equals(objectType) || ObjectType.ENTERPRISEPEOPLE.equals(objectType)) {
                QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
                queryWrapper.lambda().eq(AgreementEntity::getPartyB, objectId).eq(AgreementEntity::getPartyBId, objectId)
                        .eq(AgreementEntity::getAgreementType, agreementType);
                agreementEntity = baseMapper.selectOne(queryWrapper);
            }

        }

        if (null == agreementEntity) {
            agreementEntity = new AgreementEntity();
        }

        agreementEntity.setAgreementType(agreementType);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.APPROVED);
        agreementEntity.setSignState(SignState.SIGNED);
        agreementEntity.setValidState(ValidState.VALIDING);
        agreementEntity.setAgreementNo(SnowflakeIdWorker.getSerialNumber());

        if (ObjectType.MAKERPEOPLE.equals(objectType)) {
            agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
            agreementEntity.setPartyBId(objectId);
            if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setPartyA(ObjectType.ENTERPRISEPEOPLE);
                agreementEntity.setPartyAId(enterpriseId);
            }
        } else if (ObjectType.ENTERPRISEPEOPLE.equals(objectType)) {
            agreementEntity.setPartyB(ObjectType.ENTERPRISEPEOPLE);
            agreementEntity.setPartyBId(objectId);
            if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setPartyA(ObjectType.ENTERPRISEPEOPLE);
                agreementEntity.setPartyAId(objectId);
                agreementEntity.setPartyB(ObjectType.MAKERPEOPLE);
                agreementEntity.setPartyBId(makerId);
            }
            if (AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setPartyA(ObjectType.SERVICEPEOPLE);
                agreementEntity.setPartyAId(serviceProviderId);
            }
        } else if (ObjectType.SERVICEPEOPLE.equals(objectType)) {
            agreementEntity.setPartyB(ObjectType.SERVICEPEOPLE);
            agreementEntity.setPartyBId(objectId);
            if (AgreementType.SERENTSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
                agreementEntity.setPartyA(ObjectType.SERVICEPEOPLE);
                agreementEntity.setPartyAId(objectId);
                agreementEntity.setPartyB(ObjectType.ENTERPRISEPEOPLE);
                agreementEntity.setPartyBId(enterpriseId);
            }
        } else if (ObjectType.AGENTMAINPEOPLE.equals(objectType)) {
            agreementEntity.setPartyB(ObjectType.AGENTMAINPEOPLE);
            agreementEntity.setPartyBId(objectId);
        } else if (ObjectType.RELBUREAUPEOPLE.equals(objectType)) {
            agreementEntity.setPartyB(ObjectType.RELBUREAUPEOPLE);
            agreementEntity.setPartyBId(objectId);
        } else if (ObjectType.PARTNERPEOPLE.equals(objectType)) {
            agreementEntity.setPartyB(ObjectType.PARTNERPEOPLE);
            agreementEntity.setPartyBId(objectId);
        }
        agreementEntity.setAgreementUrl(agreementUrl);
        saveOrUpdate(agreementEntity);
        if (AgreementType.MAKERJOINAGREEMENT.equals(agreementType) || AgreementType.MAKERPOWERATTORNEY.equals(agreementType)) {
            MakerEntity makerEntity = makerService.getById(objectId);

            if (AgreementType.MAKERJOINAGREEMENT.equals(agreementType) && VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                //判断创客是否有有效的创客授权书
                int makerPowerAttorneyNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, objectId, AgreementType.MAKERPOWERATTORNEY);
                if (makerPowerAttorneyNum > 0) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }

            if (AgreementType.MAKERPOWERATTORNEY.equals(agreementType) && VerifyStatus.VERIFYPASS.equals(makerEntity.getFaceVerifyStatus())) {
                //判断创客是否有有效的创客加盟协议
                int makerJoinAgreementNum = agreementService.queryValidAgreementNum(null, null, ObjectType.MAKERPEOPLE, objectId, AgreementType.MAKERJOINAGREEMENT);
                if (makerJoinAgreementNum > 0) {
                    makerEntity.setCertificationState(CertificationState.CERTIFIED);
                    makerEntity.setCertificationDate(new Date());
                }
            }
            makerService.saveOrUpdate(makerEntity);
        }

        if (AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT.equals(agreementType)) {
            if (ObjectType.MAKERPEOPLE.equals(objectType)) {
                makerEnterpriseService.makerEnterpriseEntitySave(enterpriseId, objectId);
            }
            if (ObjectType.ENTERPRISEPEOPLE.equals(objectType)) {
                makerEnterpriseService.makerEnterpriseEntitySave(objectId, makerId);
            }
        }

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public R queryAdminEnterpriseId(Long enterpriseId, AgreementType agreementType) {
        if (!(AgreementType.ENTERPRISEJOINAGREEMENT.equals(agreementType) || AgreementType.ENTERPRISEPRICEAGREEMENT.equals(agreementType))) {
            return R.fail("合同协议类别有误");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.ENTERPRISEPEOPLE).eq(AgreementEntity::getPartyBId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        return R.data(baseMapper.selectList(queryWrapper));
    }

    @Override
    public R findAdminSerIdAgreement(Long serviceProviderId, AgreementType agreementType) {
        if (!(AgreementType.SERVICEPROVIDERJOINAGREEMENT.equals(agreementType))) {
            return R.fail("合同协议类别有误");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.SERVICEPEOPLE).eq(AgreementEntity::getPartyBId, serviceProviderId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        List<AgreementEntity> agreementEntities = baseMapper.selectList(queryWrapper);
        return R.data(agreementEntities.get(0));
    }

    @Override
    public R<String> queryOnlineAgreementUrl(Long agreementId) {
        return R.data(baseMapper.queryOnlineAgreementUrl(agreementId));
    }

    @Override
    public R queryMakerAgreementState(String makerName, IPage<AgreementMakerStateAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryMakerAgreementState(makerName, page)));
    }

    @Override
    public R<IPage<AgreementMakerEnterAdminVO>> queryEnterpriseMakerSupplement(Long enterpriseId, Long makerId, IPage<AgreementMakerEnterAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseMakerSupplement(enterpriseId, makerId, page)));
    }

    @Override
    public R<IPage<AgreementEnterpriseStateAdminVO>> queryEnterpriseAgreementState(String enterpriseName, IPage<AgreementEnterpriseStateAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseAgreementState(enterpriseName, page)));
    }

    @Override
    public R<IPage<AgreementServiceVO>> queryEnterpriseServiceProviderSupplement(Long serviceProviderId, Long enterpriseId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterpriseServiceProviderSupplement(serviceProviderId, enterpriseId, page)));
    }

    @Override
    public R<IPage<AgreementServiceVO>> queryEnterIdPromise(Long enterpriseId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryEnterIdPromise(enterpriseId, page)));
    }

    @Override
    public R<IPage<AgreementServiceStateAdminVO>> queryServiceAgreementState(String serviceProviderName, IPage<AgreementServiceStateAdminVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceAgreementState(serviceProviderName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> saveAdminAgreementId(Long agreementId, String agreementUrl) {
        AgreementEntity agreementEntity = getById(agreementId);
        if (null == agreementEntity) {
            return R.fail("合同不存在");
        }

        agreementEntity.setAgreementUrl(agreementUrl);
        updateById(agreementEntity);

        return R.success(BladeConstant.DEFAULT_SUCCESS_MESSAGE);
    }

    @Override
    public int queryValidAgreementNum(ObjectType partyA, Long partyAId, ObjectType partyB, Long partyBId, AgreementType agreementType) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(partyA != null, AgreementEntity::getPartyA, partyA).eq(partyAId != null, AgreementEntity::getPartyAId, partyAId)
                .eq(AgreementEntity::getPartyB, partyB).eq(AgreementEntity::getPartyBId, partyBId)
                .eq(AgreementEntity::getAgreementType, agreementType).eq(AgreementEntity::getValidState, ValidState.VALIDING);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public AgreementEntity findByEnterpriseAndMakerSuppl(Long makerId, Long enterpriseId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyA, ObjectType.ENTERPRISEPEOPLE).eq(AgreementEntity::getPartyAId, enterpriseId)
                .eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getAgreementType, AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT)
                .eq(AgreementEntity::getSignState, SignState.SIGNED)
                .eq(AgreementEntity::getAuditState, AuditState.APPROVED);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return agreementEntity;
    }

    @Override
    public List<AgreementEntity> findByserviceProviderId(Long serviceProviderId, Long makerId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyA, ObjectType.SERVICEPEOPLE).eq(AgreementEntity::getPartyAId, serviceProviderId)
                .eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getAgreementType, AgreementType.SERMAKSUPPLEMENTARYAGREEMENT);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public R uploadSupplementAgreementTemplate(Long objectId, ObjectType objectType, String agreementUrl,AgreementType agreementType) {
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = iOnlineAgreementTemplateService.findEntSerTemplateType(objectId, objectType, agreementType);
        if(null == onlineAgreementTemplateEntity){
            onlineAgreementTemplateEntity = new OnlineAgreementTemplateEntity();
            onlineAgreementTemplateEntity.setObjectId(objectId);
            onlineAgreementTemplateEntity.setObjectType(objectType);
            if(ObjectType.ENTERPRISEPEOPLE.equals(objectType)){
                onlineAgreementTemplateEntity.setAgreementType(AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT);
            }

            if(ObjectType.SERVICEPEOPLE.equals(objectType)){
                onlineAgreementTemplateEntity.setAgreementType(AgreementType.SERMAKSUPPLEMENTARYAGREEMENT);
            }
            onlineAgreementTemplateEntity.setTemplateState(TemplateState.APPLICATION);
            onlineAgreementTemplateEntity.setAgreementTemplate(agreementUrl);
            onlineAgreementTemplateEntity.setBoolAllSign(true);
            onlineAgreementTemplateEntity.setTemplateType(TemplateType.CONTRACT);
            onlineAgreementTemplateEntity.setTemplateCount(1);
            onlineAgreementTemplateEntity.setXCoordinate(100f);
            onlineAgreementTemplateEntity.setYCoordinate(150f);
            iOnlineAgreementTemplateService.save(onlineAgreementTemplateEntity);
        }else{
            onlineAgreementTemplateEntity.setTemplateState(TemplateState.APPLICATION);
            onlineAgreementTemplateEntity.setAgreementTemplate(agreementUrl);
            iOnlineAgreementTemplateService.saveOrUpdate(onlineAgreementTemplateEntity);
        }
        return R.success("修改成功");
    }

    @Override
    public R querySupplementAgreement(Long objectId, ObjectType objectType, AgreementType agreementType) {
        OnlineAgreementTemplateEntity entSerTemplateType = iOnlineAgreementTemplateService.findEntSerTemplateType(objectId, objectType, agreementType);
        return R.data(entSerTemplateType);
    }

    @Override
    public R queryContractTemplate() {
        OnlineAgreementTemplateEntity makerJoinAgreement = iOnlineAgreementTemplateService.findTemplateType(AgreementType.MAKERJOINAGREEMENT, true);
        OnlineAgreementTemplateEntity makerPowerAttorney = iOnlineAgreementTemplateService.findTemplateType(AgreementType.MAKERPOWERATTORNEY, true);
        OnlineAgreementTemplateEntity entMakSupplementaryAgreement = iOnlineAgreementTemplateService.findTemplateType(AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT, true);
        OnlineAgreementTemplateEntity serMakSupplementaryAgreement = iOnlineAgreementTemplateService.findTemplateType(AgreementType.SERMAKSUPPLEMENTARYAGREEMENT, true);
        List<OnlineAgreementTemplateEntity> agreementTemplateEntities = new ArrayList<>();
        agreementTemplateEntities.add(makerJoinAgreement);
        agreementTemplateEntities.add(makerPowerAttorney);
        agreementTemplateEntities.add(entMakSupplementaryAgreement);
        agreementTemplateEntities.add(serMakSupplementaryAgreement);
        return R.data(agreementTemplateEntities);
    }

    @Override
    public AgreementEntity findByServiceProviderAndMakerSuppl(Long makerId, Long serviceProviderId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyA, ObjectType.SERVICEPEOPLE).eq(AgreementEntity::getPartyAId, serviceProviderId)
                .eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getAgreementType, AgreementType.SERMAKSUPPLEMENTARYAGREEMENT)
                .eq(AgreementEntity::getSignState, SignState.SIGNED)
                .eq(AgreementEntity::getAuditState, AuditState.APPROVED);
        List<AgreementEntity> agreementEntities = baseMapper.selectList(queryWrapper);
        if(null == agreementEntities || agreementEntities.size() == 0){
            return null;
        }
        return agreementEntities.get(0);
    }

    @Override
    public R<IPage<AgreementServiceVO>> queryServiceProviderToMakerSupplementList(Long serviceProviderId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.queryServiceProviderToMakerSupplementList(serviceProviderId, page)));
    }

    @Override
    public AgreementEntity findAdminMakerId1(Long makerId, AgreementType agreementType) {
        if (!(AgreementType.MAKERJOINAGREEMENT.equals(agreementType) || AgreementType.MAKERPOWERATTORNEY.equals(agreementType))) {
            return null;
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getPartyB, ObjectType.MAKERPEOPLE).eq(AgreementEntity::getPartyBId, makerId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return agreementEntity;
    }

}
