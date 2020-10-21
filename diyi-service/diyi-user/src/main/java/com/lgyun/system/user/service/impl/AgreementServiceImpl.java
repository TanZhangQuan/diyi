package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.*;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.common.tool.PDFUtil;
import com.lgyun.common.tool.StringUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.oss.AliyunOssService;
import com.lgyun.system.user.service.*;
import com.lgyun.system.user.vo.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final IAgentMainService agentMainService;
    private final IPartnerService partnerService;

    @Autowired
    @Lazy
    private IOnlineSignPicService onlineSignPicService;

    @Autowired
    @Lazy
    private AliyunOssService ossService;

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
    public List<AgreementEntity> findByEnterpriseId(Long enterpriseId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(AgreementEntity::getSignType, SignType.PLATFORMAGREEMENT);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public AgreementWebVO findByEnterpriseAndType(Long enterpriseId, AgreementType agreementType) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .eq(AgreementEntity::getAgreementType, agreementType)
                .eq(AgreementEntity::getSignType, SignType.PLATFORMAGREEMENT);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        AgreementWebVO agreementWebVO = BeanUtil.copy(agreementEntity, AgreementWebVO.class);
        agreementWebVO.setEnterpriseName(byId.getEnterpriseName());
        return agreementWebVO;
    }

    @Override
    public R selectAuthorization(Long enterpriseId, IPage<AgreementEntity> page) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .in(AgreementEntity::getSignType, SignType.UNILATERALPOWER, SignType.ELEUNILATERALPOWER);
        IPage<AgreementEntity> agreementEntityIPage = baseMapper.selectPage(page, queryWrapper);
        return R.data(agreementEntityIPage);
    }

    @Override
    public R<String> saveAuthorization(Long enterpriseId, String paperAgreementURL) {
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.OTHERAGREEMENT);
        agreementEntity.setSignType(SignType.UNILATERALPOWER);
        agreementEntity.setSignState(SignState.UNSIGN);
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setPaperAgreementUrl(paperAgreementURL);
        agreementEntity.setFirstSideSignPerson("地衣众包");
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("上传成功");
    }

    @Override
    public IPage<AgreementWebVO> selectServiceAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, SignType signType, AgreementType agreementType) {
        return page.setRecords(baseMapper.selectServiceAgreement(enterpriseId, serviceProviderName, agreementNo, signType, agreementType, page));
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
    public R saveEnterpriseMakerAgreement(Long enterpriseId, String paperAgreementURL) {
        if (StringUtil.isBlank(paperAgreementURL)) {
            return R.fail("参数错误");
        }
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(AgreementType.ENTMAKSUPPLEMENTARYAGREEMENT);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setPaperAgreementUrl(paperAgreementURL);
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setFirstSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("上传成功");
    }

    @Override
    public R selectEnterpriseMakerAgreement(IPage<AgreementMakerWebVO> page, Long enterpriseId) {
        return R.data(page.setRecords(baseMapper.selectEnterpriseMakerAgreement(enterpriseId, page)));
    }

    @Override
    @Transactional
    public R saveOnlineAgreement(Long enterpriseId, String paperAgreementURL, Boolean boolAllMakers, String makerIds, Integer templateCount, AgreementType agreementType, IMakerEnterpriseService makerEnterpriseService) throws Exception {
        if (boolAllMakers && StringUtil.isBlank(makerIds)) {
            return R.fail("创客id不能为空");
        }
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        PDFUtil pdfUtil = new PDFUtil();
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = new OnlineAgreementTemplateEntity();
        onlineAgreementTemplateEntity.setAgreementType(agreementType);
        onlineAgreementTemplateEntity.setAgreementTemplate(paperAgreementURL);
        onlineAgreementTemplateEntity.setUploadPerson(byId.getEnterpriseName());
        onlineAgreementTemplateEntity.setUploadDate(new Date());
        onlineAgreementTemplateEntity.setBoolAllMakers(boolAllMakers);
        onlineAgreementTemplateEntity.setTemplateCount(templateCount);
        onlineAgreementTemplateEntity.setTemplateType(TemplateType.CONTRACT);
        iOnlineAgreementTemplateService.save(onlineAgreementTemplateEntity);
        if (boolAllMakers) {
            String[] split = makerIds.split(",");
            for (int i = 0; i < split.length; i++) {
                OnlineSignPicEntity onlineSignPicEntity = onlineSignPicService.findObjectTypeAndObjectId(Long.parseLong(split[i]), ObjectType.MAKERPEOPLE);
                if (null != onlineSignPicEntity) {
                    Map map = pdfUtil.addPdf(paperAgreementURL, templateCount, onlineSignPicEntity.getSignPic());
                    FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
                    File file = (File) map.get("htmlFile");
                    String pdf = ossService.uploadSuffix(fileInputStream, ".pdf");
                    fileInputStream.close();
                    file.delete();
                    AgreementEntity agreementEntity = new AgreementEntity();
                    agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
                    agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
                    agreementEntity.setMakerId(Long.parseLong(split[i]));
                    agreementEntity.setEnterpriseId(enterpriseId);
                    agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
                    agreementEntity.setOnlineAgreementUrl(pdf);
                    agreementEntity.setFirstSideSignPerson(byId.getEnterpriseName());
                    MakerEntity makerEntity = makerService.getById(Long.parseLong(split[i]));
                    agreementEntity.setSecondSideSignPerson(makerEntity.getName());
                    agreementService.save(agreementEntity);
                } else {
                    OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity = new OnlineAgreementNeedSignEntity();
                    onlineAgreementNeedSignEntity.setObjectId(Long.parseLong(split[i]));
                    onlineAgreementNeedSignEntity.setObjectType(ObjectType.MAKERPEOPLE);
                    onlineAgreementNeedSignEntity.setSignState(SignState.UNSIGN);
                    onlineAgreementNeedSignEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
                    onlineAgreementNeedSignEntity.setSignPower(SignPower.PARTYB);
                    onlineAgreementNeedSignService.save(onlineAgreementNeedSignEntity);
                }
            }
        } else {
            List<MakerEnterpriseEntity> enterpriseId1 = makerEnterpriseService.getEnterpriseId(enterpriseId);
            for (MakerEnterpriseEntity makerEnterpriseEntity : enterpriseId1) {
                OnlineSignPicEntity onlineSignPicEntity = onlineSignPicService.findObjectTypeAndObjectId(makerEnterpriseEntity.getMakerId(), ObjectType.MAKERPEOPLE);
                if (null != onlineSignPicEntity) {
                    Map map = pdfUtil.addPdf(paperAgreementURL, templateCount, onlineSignPicEntity.getSignPic());
                    FileInputStream fileInputStream = (FileInputStream) map.get("fileInputStream");
                    File file = (File) map.get("htmlFile");
                    String pdf = ossService.uploadSuffix(fileInputStream, ".pdf");
                    fileInputStream.close();
                    file.delete();
                    AgreementEntity agreementEntity = new AgreementEntity();
                    agreementEntity.setAgreementType(onlineAgreementTemplateEntity.getAgreementType());
                    agreementEntity.setSignType(SignType.PLATFORMAGREEMENT);
                    agreementEntity.setMakerId(makerEnterpriseEntity.getMakerId());
                    agreementEntity.setEnterpriseId(enterpriseId);
                    agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
                    agreementEntity.setOnlineAgreementUrl(pdf);
                    agreementEntity.setFirstSideSignPerson(byId.getEnterpriseName());
                    agreementEntity.setSecondSideSignPerson(makerService.getById(makerEnterpriseEntity.getMakerId()).getName());
                    agreementService.save(agreementEntity);
                } else {
                    OnlineAgreementNeedSignEntity onlineAgreementNeedSignEntity = new OnlineAgreementNeedSignEntity();
                    onlineAgreementNeedSignEntity.setObjectId(makerEnterpriseEntity.getMakerId());
                    onlineAgreementNeedSignEntity.setObjectType(ObjectType.MAKERPEOPLE);
                    onlineAgreementNeedSignEntity.setSignState(SignState.UNSIGN);
                    onlineAgreementNeedSignEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
                    onlineAgreementNeedSignEntity.setSignPower(SignPower.PARTYB);
                    onlineAgreementNeedSignService.save(onlineAgreementNeedSignEntity);
                }
            }
        }
        return R.success("上传成功");
    }

    @Override
    public R findSeriveAgreement(String agreementNo, Long serviceProviderId, IPage<AgreementServiceVO> page) {
        return R.data(page.setRecords(baseMapper.findSeriveAgreement(agreementNo, serviceProviderId, page)));
    }

    @Override
    @Transactional
    public R uploadContractAndLetter(String contractUrl, String letterUrl, Long serviceProviderId) {

        if (StringUtil.isBlank(contractUrl) && StringUtil.isBlank(letterUrl)) {
            return R.fail("加盟合同和服务商承诺函不能同时为空");
        }

        if (!StringUtil.isBlank(contractUrl)) {
            saveContractAndLetter(AgreementType.SERVICEPROVIDERJOINAGREEMENT, contractUrl, ObjectType.SERVICEPEOPLE, serviceProviderId);
        }

        if (!StringUtil.isBlank(letterUrl)) {
            saveContractAndLetter(AgreementType.OTHERAGREEMENT, letterUrl, ObjectType.SERVICEPEOPLE, serviceProviderId);
        }

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
    public R<AgreementEntity> findAdminMakerId(Long makerId, AgreementType agreementType) {
        if (!(AgreementType.MAKERJOINAGREEMENT.equals(agreementType) || AgreementType.MAKERPOWERATTORNEY.equals(agreementType))) {
            return R.fail("合同协议类别错误!!!");
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
    public R saveAdminAgreement(Long agreementId, String name, Long objectId, ObjectType objectType, Integer contractType, AgreementType agreementType, String paperAgreementUrl) {
        AgreementEntity agreementEntity = null;
        agreementEntity = agreementService.getById(agreementId);
        if (null != agreementEntity) {
            agreementEntity.setPaperAgreementUrl(paperAgreementUrl);
            agreementEntity.setSignType(SignType.PAPERAGREEMENT);
            agreementEntity.setSignState(SignState.SIGNED);
            agreementEntity.setOnlineAgreementUrl("");
            agreementEntity.setUpdateTime(new Date());
            saveOrUpdate(agreementEntity);
            return R.success("编辑成功");
        }
        agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(agreementType);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setSignState(SignState.SIGNED);
        if (ObjectType.MAKERPEOPLE.equals(objectType)) {
            agreementEntity.setMakerId(objectId);
        } else if (ObjectType.ENTERPRISEPEOPLE.equals(objectType)) {
            agreementEntity.setEnterpriseId(objectId);
        } else if (ObjectType.SERVICEPEOPLE.equals(objectType)) {
            agreementEntity.setServiceProviderId(objectId);
        } else if (ObjectType.CHANNELPEOPLE.equals(objectType)) {
            agreementEntity.setAgentId(objectId);
        } else if (ObjectType.RELEVANTPEOPLE.equals(objectType)) {
            agreementEntity.setRelBureauId(objectId);
        } else if (ObjectType.PARTNERSHIPPEOPLE.equals(objectType)) {
            agreementEntity.setPartnerId(objectId);
        }
        agreementEntity.setPaperAgreementUrl(paperAgreementUrl);
        agreementEntity.setFirstSideSignPerson("地衣平台");
        agreementEntity.setSecondSideSignPerson(name);
        save(agreementEntity);
        return R.success("操作成功");
    }

    @Override
    public R findAdminEnterpriseId(Long enterpriseId, AgreementType agreementType) {
        if (!(AgreementType.ENTERPRISEJOINAGREEMENT.equals(agreementType) || AgreementType.ENTERPRISEPOWERATTORNEY.equals(agreementType))) {
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
        if (!(AgreementType.SERVICEPROVIDERJOINAGREEMENT.equals(agreementType) || AgreementType.ENTERPRISEPOWERATTORNEY.equals(agreementType))) {
            return R.fail("合同协议类别错误!!!");
        }
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getServiceProviderId, serviceProviderId)
                .eq(AgreementEntity::getAgreementType, agreementType);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        return R.data(agreementEntity);
    }

    @Override
    public R<String> queryOnlineAgreementUrl(Long agreementId) {
        AgreementEntity agreementEntity = agreementService.getById(agreementId);
        if (null == agreementEntity) {
            return R.fail("合同不存在");
        }

        return R.data(agreementEntity.getOnlineAgreementUrl());
    }

    private R<String> saveContractAndLetter(AgreementType agreementType, String file, ObjectType objectType, Long objectId) {

        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(agreementType);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setPaperAgreementUrl(file);
        agreementEntity.setFirstSideSignPerson("地衣平台");

        switch (objectType) {

            case MAKERPEOPLE:
                MakerEntity makerEntity = makerService.getById(objectId);
                if (makerEntity == null) {
                    return R.fail("创客不存在");
                }

                agreementEntity.setMakerId(objectId);
                agreementEntity.setSecondSideSignPerson(makerEntity.getName());
                break;

            case ENTERPRISEPEOPLE:
                EnterpriseEntity enterpriseEntity = enterpriseService.getById(objectId);
                if (enterpriseEntity == null) {
                    return R.fail("商户不存在");
                }

                agreementEntity.setMakerId(objectId);
                agreementEntity.setSecondSideSignPerson(enterpriseEntity.getContact1Name());
                break;

            case SERVICEPEOPLE:
                ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(objectId);
                if (serviceProviderEntity == null) {
                    return R.fail("服务商不存在");
                }

                agreementEntity.setMakerId(objectId);
                agreementEntity.setSecondSideSignPerson(serviceProviderEntity.getContact1Name());
                break;

            case CHANNELPEOPLE:
                AgentMainEntity agentMainEntity = agentMainService.getById(objectId);
                if (agentMainEntity == null) {
                    return R.fail("渠道商不存在");
                }

                agreementEntity.setMakerId(objectId);
                agreementEntity.setSecondSideSignPerson(agentMainEntity.getContact1Name());
                break;

            case RELEVANTPEOPLE:
                break;

            case PARTNERSHIPPEOPLE:
                PartnerEntity partnerEntity = partnerService.getById(objectId);
                if (partnerEntity == null) {
                    return R.fail("合伙人不存在");
                }

                agreementEntity.setMakerId(objectId);
                agreementEntity.setSecondSideSignPerson(partnerEntity.getName());
                break;

            default:
                return R.fail("用户类型不存在");
        }

        agreementService.save(agreementEntity);

        return R.success("操作成功");
    }

}
