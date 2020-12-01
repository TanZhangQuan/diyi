package com.lgyun.system.user.service;

import com.lgyun.common.enumeration.EnterpriseRule;
import com.lgyun.common.enumeration.MakerRule;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.ServiceProviderRulesEntity;

import java.util.Set;

/**
 * 服务商-业务合规要求表 Service 接口
 *
 * @author tzq
 * @since 2020-12-01 10:55:04
 */
public interface IServiceProviderRulesService extends BaseService<ServiceProviderRulesEntity> {

    /**
     * 根据服务商查询创客业务规则
     *
     * @param serviceProviderId
     */
    Set<MakerRule> queryMakerRuleByServiceProvider(Long serviceProviderId);

    /**
     * 根据服务商查询创客业务规则
     *
     * @param serviceProviderId
     */
    Set<EnterpriseRule> queryEnterpriseRuleByServiceProvider(Long serviceProviderId);

    /**
     * 根据服务商查询业务规则
     *
     * @param serviceProviderId
     */
    ServiceProviderRulesEntity queryByServiceProvider(Long serviceProviderId);

    /**
     * 新建服务商-创客业务规则，服务商-商户业务规则
     *
     * @param serviceProviderId
     * @param makerRuleHashSet
     * @param enterpriseRuleSet
     */
    void addOrUpdateServiceProviderRules(Long serviceProviderId, Set<MakerRule> makerRuleHashSet, Set<EnterpriseRule> enterpriseRuleSet);
}

