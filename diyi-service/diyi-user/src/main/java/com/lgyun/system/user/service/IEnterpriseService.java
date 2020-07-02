package com.lgyun.system.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgyun.common.api.R;
import com.lgyun.system.user.entity.EnterpriseEntity;

/**
 *  Service 接口
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
public interface IEnterpriseService extends IService<EnterpriseEntity> {

    /**
     * 通过商户名字查询
     */
    R getEnterpriseName(String enterpriseName);
}

