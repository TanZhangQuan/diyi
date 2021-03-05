package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.AgreementType;
import com.lgyun.common.enumeration.ObjectType;
import com.lgyun.common.enumeration.TemplateState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.mapper.OnlineAgreementTemplateMapper;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service 实现
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineAgreementTemplateServiceImpl extends BaseServiceImpl<OnlineAgreementTemplateMapper, OnlineAgreementTemplateEntity> implements IOnlineAgreementTemplateService {

    @Override
    public OnlineAgreementTemplateEntity findTemplateType(AgreementType agreementType, Boolean boolAllSign) {
        QueryWrapper<OnlineAgreementTemplateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineAgreementTemplateEntity::getAgreementType, agreementType)
                .eq(OnlineAgreementTemplateEntity::getTemplateState, TemplateState.APPLICATION)
                .eq(OnlineAgreementTemplateEntity::getBoolAllSign, boolAllSign)
                .eq(OnlineAgreementTemplateEntity::getObjectId, 0);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public OnlineAgreementTemplateEntity findEntSerTemplateType(Long objectId, ObjectType objectType, AgreementType agreementType) {
        QueryWrapper<OnlineAgreementTemplateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineAgreementTemplateEntity::getObjectType,objectType)
                .eq(OnlineAgreementTemplateEntity::getObjectId,objectId)
                .eq(OnlineAgreementTemplateEntity::getAgreementType,agreementType);
        OnlineAgreementTemplateEntity onlineAgreementTemplateEntity = baseMapper.selectOne(queryWrapper);
        return onlineAgreementTemplateEntity;
    }
}
