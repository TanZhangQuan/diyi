package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.AgreementEntity;

import java.util.List;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:06
 */
public interface IAgreementService extends IService<AgreementEntity> {
    /**
     * 根据创客找合同
     */
    List<AgreementEntity> makerIdFind(Long makerId);

    /**
     * 根据创客和商户找合同
     */
    List<AgreementEntity> makerIdCompanyFind(Long makerId,Long employeeId);
}

