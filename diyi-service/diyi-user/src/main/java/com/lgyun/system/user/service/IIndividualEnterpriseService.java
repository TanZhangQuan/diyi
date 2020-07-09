package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.common.secure.BladeUser;
import com.lgyun.system.user.dto.IndividualEnterpriseAddDto;
import com.lgyun.system.user.entity.IndividualEnterpriseEntity;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-07-02 17:44:02
 */
public interface IIndividualEnterpriseService extends IService<IndividualEnterpriseEntity> {

    //新增个独
    R save(IndividualEnterpriseAddDto individualEnterpriseAddDto, BladeUser bladeUser);

    IndividualEnterpriseEntity findMakerId(Long makerId);
}

