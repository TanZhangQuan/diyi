package com.lgyun.system.user.service;

import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.EnterpriseRule;
import com.lgyun.common.enumeration.MakerRule;
import com.lgyun.core.mp.base.BaseService;
import com.lgyun.system.user.entity.ServiceProviderRuleEntity;
import com.lgyun.system.user.vo.ServiceProviderRuleVO;

import java.util.Set;

/**
 * 服务商-业务合规要求表 Service 接口
 *
 * @author tzq
 * @since 2020-12-01 10:55:04
 */
public interface IServiceProviderRulesService extends BaseService<ServiceProviderRuleEntity> {

    /**
     * 添加或编辑服务商-创客业务规则，服务商-商户业务规则
     *
     * @param serviceProviderId
     * @param makerRuleSet
     * @param enterpriseRuleSet
     */
    R<String> addOrUpdateServiceProviderRule(Long serviceProviderId, Set<MakerRule> makerRuleSet, Set<EnterpriseRule> enterpriseRuleSet);

    /**
     * 根据服务商查询业务规则
     *
     * @param serviceProviderId
     */
    ServiceProviderRuleEntity queryByServiceProvider(Long serviceProviderId);

    /**
     * 根据服务商查询业务规则
     *
     * @param serviceProviderId
     * @return
     */
    R<ServiceProviderRuleVO> queryServiceProviderRule(Long serviceProviderId);

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
     * 根据服务商, 创客处理业务规则
     *
     * @param serviceProviderId
     * @param makerId
     */
    R<String> dealMakerRule(Long serviceProviderId, Long makerId);

    /**
     * 根据服务商, 商户处理业务规则
     *
     * @param serviceProviderId
     * @param enterpriseId
     */
    R<String> dealEnterpriseRule(Long serviceProviderId, Long enterpriseId);

}

