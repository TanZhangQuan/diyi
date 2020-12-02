package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgyun.common.enumeration.EnterpriseRule;
import com.lgyun.common.enumeration.MakerRule;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.ServiceProviderRulesEntity;
import com.lgyun.system.user.mapper.ServiceProviderRulesMapper;
import com.lgyun.system.user.service.IServiceProviderRulesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * 服务商-业务合规要求表 Service 实现
 *
 * @author tzq
 * @since 2020-12-01 10:55:04
 */
@Slf4j
@Service
@AllArgsConstructor
public class ServiceProviderRulesServiceImpl extends BaseServiceImpl<ServiceProviderRulesMapper, ServiceProviderRulesEntity> implements IServiceProviderRulesService {

    @Override
    public Set<MakerRule> queryMakerRuleByServiceProvider(Long serviceProviderId) {

        Set<MakerRule> makerRuleSet = new HashSet<>();
        String makerRules = baseMapper.queryMakerRuleByServiceProvider(serviceProviderId);
        if (StringUtils.isNotBlank(makerRules)) {
            //解析服务商-创客规则
            String[] split = makerRules.split(",");
            for (String string : split) {
                if (StringUtils.isNotBlank(string)) {
                    makerRuleSet.add(Enum.valueOf(MakerRule.class, string));
                }
            }
        }

        return makerRuleSet;
    }

    @Override
    public Set<EnterpriseRule> queryEnterpriseRuleByServiceProvider(Long serviceProviderId) {

        Set<EnterpriseRule> enterpriseRuleSet = new HashSet<>();
        String enterpriseRules = baseMapper.queryEnterpriseRuleByServiceProvider(serviceProviderId);
        if (StringUtils.isNotBlank(enterpriseRules)) {
            //解析服务商-创客规则
            String[] split = enterpriseRules.split(",");
            for (String string : split) {
                if (StringUtils.isNotBlank(string)) {
                    enterpriseRuleSet.add(Enum.valueOf(EnterpriseRule.class, string));
                }
            }
        }

        return enterpriseRuleSet;
    }

    @Override
    public ServiceProviderRulesEntity queryByServiceProvider(Long serviceProviderId) {
        QueryWrapper<ServiceProviderRulesEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ServiceProviderRulesEntity::getServiceProviderId, serviceProviderId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrUpdateServiceProviderRules(Long serviceProviderId, Set<MakerRule> makerRuleHashSet, Set<EnterpriseRule> enterpriseRuleSet) {

        ServiceProviderRulesEntity serviceProviderRulesEntity = queryByServiceProvider(serviceProviderId);
        if (serviceProviderRulesEntity == null) {
            serviceProviderRulesEntity = new ServiceProviderRulesEntity();
        }

        serviceProviderRulesEntity.setServiceProviderId(serviceProviderId);
        if (makerRuleHashSet != null && !makerRuleHashSet.isEmpty()) {
            serviceProviderRulesEntity.setEnterpriseRules(StringUtils.join(enterpriseRuleSet.toArray(), ","));
        }
        if (enterpriseRuleSet != null && !enterpriseRuleSet.isEmpty()) {
            serviceProviderRulesEntity.setMakerRules(StringUtils.join(makerRuleHashSet.toArray(), ","));
        }
        saveOrUpdate(serviceProviderRulesEntity);

    }
}
