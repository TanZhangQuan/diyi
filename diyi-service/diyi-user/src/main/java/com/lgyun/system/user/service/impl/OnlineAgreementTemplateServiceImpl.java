package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.TemplateSignState;
import com.lgyun.common.enumeration.TemplateState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.mapper.OnlineAgreementTemplateMapper;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import com.lgyun.system.user.wrapper.EnterpriseWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *  Service 实现
 *
 * @author jun
 * @since 2020-07-18 14:37:08
 */
@Slf4j
@Service
@AllArgsConstructor
public class OnlineAgreementTemplateServiceImpl extends BaseServiceImpl<OnlineAgreementTemplateMapper, OnlineAgreementTemplateEntity> implements IOnlineAgreementTemplateService {

    @Override
    public OnlineAgreementTemplateEntity findTemplateType(Integer templateType) {
        QueryWrapper<OnlineAgreementTemplateEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(OnlineAgreementTemplateEntity::getTemplateType, templateType)
                .eq(OnlineAgreementTemplateEntity::getTemplateSignState, TemplateSignState.OPEN)
                .eq(OnlineAgreementTemplateEntity::getTemplateState, TemplateState.APPLICATION);
        return baseMapper.selectOne(queryWrapper);
    }
}
