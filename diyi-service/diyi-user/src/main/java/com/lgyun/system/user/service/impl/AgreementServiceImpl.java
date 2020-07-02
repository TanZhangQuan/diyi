package com.lgyun.system.user.service.impl;

import com.lgyun.common.api.R;
import com.lgyun.system.user.service.IAgreementService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lgyun.system.user.mapper.AgreementMapper;
import com.lgyun.system.user.entity.AgreementEntity;

import java.util.List;

/**
 *  Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
@Service("agreementService")
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, AgreementEntity> implements IAgreementService {

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
