package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.SignState;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.entity.OnlineAgreementTemplateEntity;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.service.IAgreementService;
import com.lgyun.system.user.service.IOnlineAgreementTemplateService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public R<Map> makerIdFind(Long makerId, Long onlineAgreementTemplateId, Long onlineAgreementNeedSignId) {
        QueryWrapper<AgreementEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, makerId)
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
        queryWrapper.lambda().eq(AgreementEntity::getEnterpriseId, enterpriseId);
        return baseMapper.selectList(queryWrapper);
    }
}
