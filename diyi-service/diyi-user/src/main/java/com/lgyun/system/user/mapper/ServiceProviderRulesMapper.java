package com.lgyun.system.user.mapper;

import com.lgyun.system.user.entity.ServiceProviderRuleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 服务商-业务合规要求表 Mapper
 *
 * @author tzq
 * @since 2020-12-01 10:55:04
 */
@Mapper
public interface ServiceProviderRulesMapper extends BaseMapper<ServiceProviderRuleEntity> {

    /**
     * 根据服务商查询创客业务规则
     *
     * @param serviceProviderId
     * @return
     */
    String queryMakerRuleByServiceProvider(Long serviceProviderId);

    /**
     * 根据服务商查询创客业务规则
     *
     * @param serviceProviderId
     * @return
     */
    String queryEnterpriseRuleByServiceProvider(Long serviceProviderId);
}

