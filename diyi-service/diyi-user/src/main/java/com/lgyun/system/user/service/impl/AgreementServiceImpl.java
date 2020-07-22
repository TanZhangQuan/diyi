package com.lgyun.system.user.service.impl;

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
 *  Service 实现
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
    public R makerIdFind(Long makerId, Long onlineAgreementTemplateId,Long onlineAgreementNeedSignId) {
        AgreementEntity agreementEntities = baseMapper.makerIdFind(makerId,onlineAgreementTemplateId);
        Map map = new HashMap();
        if(null == agreementEntities){
            OnlineAgreementTemplateEntity byId = iOnlineAgreementTemplateService.getById(onlineAgreementTemplateId);
            map.put("onlineAgreementTemplateId",onlineAgreementTemplateId);
            map.put("onlineAgreementNeedSignId",onlineAgreementNeedSignId);
            map.put("agreementTemplate",byId.getAgreementTemplate());
            map.put("signState", SignState.NOTSIGNED);
            return R.data(map);
        }
        map.put("onlineAgreementTemplateId","");
        map.put("onlineAgreementNeedSignId","");
        map.put("agreementTemplate",agreementEntities.getOnlineAggrementUrl());
        map.put("signState",agreementEntities.getSignState().getValue());
        return R.data(map);
    }

    @Override
    public List<AgreementEntity> makerIdCompanyFind( Long employeeId) {
        List<AgreementEntity> agreementEntities = baseMapper.makerIdCompanyFind(employeeId);
        return agreementEntities;
    }
}
