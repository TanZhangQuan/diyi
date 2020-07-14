package com.lgyun.system.user.service.impl;

import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.AgreementEntity;
import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.service.IAgreementService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<AgreementEntity> makerIdFind(Long makerId) {
        List<AgreementEntity> agreementEntities = baseMapper.makerIdFind(makerId);
        return agreementEntities;
    }

    @Override
    public List<AgreementEntity> makerIdCompanyFind(Long makerId, Long employeeId) {
        List<AgreementEntity> agreementEntities = baseMapper.makerIdCompanyFind(makerId,employeeId);
        return agreementEntities;
    }
}
