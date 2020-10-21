package com.lgyun.system.user.service;

import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.AgentPersonEntity;

/**
 * 渠道商人员表 Service 接口
 *
 * @author liangfeihu
 * @since 2020-10-20 18:25:03
 */
public interface IAgentPersonService extends BaseService<AgentPersonEntity> {
    /**
     * 根据手机号码查询商户员工是否存在
     *
     * @param phoneNumber
     * @return
     */
    Integer findCountByPhoneNumber(String phoneNumber);
}

