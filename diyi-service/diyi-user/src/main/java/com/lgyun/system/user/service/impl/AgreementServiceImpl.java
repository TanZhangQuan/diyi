package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.AuditState;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.common.enumeration.SignType;
import com.lgyun.common.tool.BeanUtil;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.entity.ServiceProviderEntity;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.AgreementWebVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgreementServiceImpl extends BaseServiceImpl<AgreementMapper, AgreementEntity> implements IAgreementService {

    private IOnlineAgreementTemplateService iOnlineAgreementTemplateService;
    private IEnterpriseService enterpriseService;
    private IServiceProviderService serviceProviderService;

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
        map.put("agreementTemplate", agreementEntity.getOnlineAggrementUrl());
        map.put("signState", agreementEntity.getSignState().getValue());
        return R.data(map);
    }

    @Override
    public List<AgreementEntity> findByEnterpriseId(Long enterpriseId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId).eq(AgreementEntity::getSignType,SignType.PLATFORMAGREEMENT);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public AgreementWebVO findByEnterpriseAndType(Long enterpriseId, Integer agreementType) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                            .eq(AgreementEntity::getAgreementType,agreementType)
                            .eq(AgreementEntity::getSignType,SignType.PLATFORMAGREEMENT);
        AgreementEntity agreementEntity = baseMapper.selectOne(queryWrapper);
        AgreementWebVO agreementWebVO = BeanUtil.copy(agreementEntity, AgreementWebVO.class);
        return agreementWebVO;
    }

    @Override
    public List<AgreementWebVO> selectAuthorization(Long enterpriseId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId)
                .in(AgreementEntity::getSignType,SignType.UNILATERALPOWER,SignType.ELEUNILATERALPOWER);
        List<AgreementEntity> agreementEntities = baseMapper.selectList(queryWrapper);
        List<AgreementWebVO> agreementWebVOS = new ArrayList<>();
        for (AgreementEntity agreementEntity : agreementEntities){
            AgreementWebVO agreementWebVO = BeanUtil.copy(agreementEntity, AgreementWebVO.class);
            agreementWebVOS.add(agreementWebVO);
        }
        return agreementWebVOS;
    }

    @Override
    public R saveAuthorization(Long enterpriseId,String paperAgreementURL) {
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = iOnlineAgreementTemplateService.findTemplateType(14);
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(14);
        agreementEntity.setSignType(SignType.UNILATERALPOWER);
        agreementEntity.setAuditState(AuditState.EDITING);
        agreementEntity.setSignState(SignState.SIGNING);
        agreementEntity.setSignDate(new Date());
        agreementEntity.setAgreementNo(UUID.randomUUID().toString());
        agreementEntity.setSequenceNo(UUID.randomUUID().toString());
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
        agreementEntity.setPaperAgreementUrl(paperAgreementURL);
        agreementEntity.setFirstSideSignPerson("地衣众包");
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("上传成功");
    }

    @Override
    public IPage<AgreementWebVO> selectServiceAgreement(IPage<AgreementWebVO> page, Long enterpriseId,String serviceProviderName,String agreementNo,SignType signType,Integer agreementType) {
        return page.setRecords(baseMapper.selectServiceAgreement(enterpriseId,serviceProviderName,agreementNo,signType,agreementType,page));
    }

    @Override
    public IPage<AgreementWebVO> selectServiceSupplementaryAgreement(IPage<AgreementWebVO> page, Long enterpriseId, String serviceProviderName, String agreementNo, Integer agreementType) {
        return page.setRecords(baseMapper.selectServiceSupplementaryAgreement(enterpriseId,serviceProviderName,agreementNo,agreementType,page));
    }

    @Override
    public R saveSupplementaryAgreement(Long enterpriseId, String paperAgreementURL, Long serviceProviderId) {
        EnterpriseEntity byId = enterpriseService.getById(enterpriseId);
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = iOnlineAgreementTemplateService.findTemplateType(11);
        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        AgreementEntity agreementEntity = new AgreementEntity();
        agreementEntity.setAgreementType(11);
        agreementEntity.setSignType(SignType.PAPERAGREEMENT);
        agreementEntity.setAuditState(AuditState.EDITING);
        agreementEntity.setSignState(SignState.SIGNING);
        agreementEntity.setSignDate(new Date());
        agreementEntity.setAgreementNo(UUID.randomUUID().toString());
        agreementEntity.setSequenceNo(UUID.randomUUID().toString());
        agreementEntity.setEnterpriseId(enterpriseId);
        agreementEntity.setServiceProviderId(serviceProviderId);
        agreementEntity.setOnlineAgreementTemplateId(onlineAgreementTemplateEntity.getId());
        agreementEntity.setPaperAgreementUrl(paperAgreementURL);
        agreementEntity.setFirstSideSignPerson(serviceProviderEntity.getServiceProviderUserName());
        agreementEntity.setSecondSideSignPerson(byId.getEnterpriseName());
        agreementEntity.setUploadDatetime(new Date());
        agreementEntity.setUploadPerson(byId.getEnterpriseName());
        save(agreementEntity);
        return R.success("上传成功");
    }
}
