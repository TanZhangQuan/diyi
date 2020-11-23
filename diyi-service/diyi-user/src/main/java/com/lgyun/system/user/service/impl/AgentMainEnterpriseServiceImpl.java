package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.CooperateType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.AgentMainEnterpriseMapper;
import com.lgyun.system.user.service.IAgentMainEnterpriseService;
import com.lgyun.system.user.service.IAgentMainService;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 渠道商-商户关联表 Service 实现
 *
 * @author tzq
 * @since 2020-09-11 17:33:26
 */
@Slf4j
@Service
@AllArgsConstructor
public class AgentMainEnterpriseServiceImpl extends BaseServiceImpl<AgentMainEnterpriseMapper, AgentMainEnterpriseEntity> implements IAgentMainEnterpriseService {

    private IEnterpriseService enterpriseService;
    private IAgentMainService agentMainService;

    @Override
    public R<String> relevanceAgentMainEnterprise(Long agentMainId, Long enterpriseId, String matchDesc, AdminEntity adminEntity) {

        AgentMainEntity agentMainEntity = agentMainService.getById(agentMainId);
        if (agentMainEntity == null) {
            return R.fail("渠道商不存在");
        }

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        AgentMainEnterpriseEntity agentMainEnterpriseEntity = queryByAgentMainAndEnterprise(agentMainId, enterpriseId);
        if (agentMainEnterpriseEntity == null) {
            agentMainEnterpriseEntity = new AgentMainEnterpriseEntity();
            agentMainEnterpriseEntity.setAgentMainId(agentMainId);
            agentMainEnterpriseEntity.setEnterpriseId(enterpriseId);
            agentMainEnterpriseEntity.setCooperateType(CooperateType.ALLOCATION);
            agentMainEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
            agentMainEnterpriseEntity.setOperatePerson(adminEntity.getName());
            agentMainEnterpriseEntity.setOperateDesc(matchDesc);
            save(agentMainEnterpriseEntity);

        } else {
            if (!(CooperateStatus.COOPERATING.equals(agentMainEnterpriseEntity.getCooperateStatus()))) {
                agentMainEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                updateById(agentMainEnterpriseEntity);
            }
        }

        return R.success("匹配商户成功");
    }

    @Override
    public AgentMainEnterpriseEntity queryByAgentMainAndEnterprise(Long agentMainId, Long enterpriseId) {
        QueryWrapper<AgentMainEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AgentMainEnterpriseEntity::getAgentMainId, agentMainId).
                eq(AgentMainEnterpriseEntity::getEnterpriseId, enterpriseId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<CooperationEnterprisesListVO>> queryCooperationEnterpriseList(Long agentMainId, String enterpriseName, IPage<CooperationEnterprisesListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationEnterpriseList(agentMainId, enterpriseName, page)));
    }

    @Override
    public R<String> updateCooperationStatus(Long agentMainId, Long enterpriseId, CooperateStatus cooperateStatus) {

        int agentMainNum = agentMainService.queryCountById(agentMainId);
        if (agentMainNum <= 0) {
            return R.fail("渠道商不存在");
        }

        int serviceProviderNum = enterpriseService.queryCountById(enterpriseId);
        if (serviceProviderNum <= 0) {
            return R.fail("商户不存在");
        }

        AgentMainEnterpriseEntity agentMainEnterpriseEntity = queryByAgentMainAndEnterprise(agentMainId, enterpriseId);
        if (agentMainEnterpriseEntity == null) {
            return R.fail("商户服务商不存在合作关系");
        }

        if (CooperateType.CREATE.equals(agentMainEnterpriseEntity.getCooperateType())) {
            return R.fail("渠道商-商户为创建关系，不可更改");
        }

        if (!(agentMainEnterpriseEntity.getCooperateStatus().equals(cooperateStatus))) {
            agentMainEnterpriseEntity.setCooperateStatus(cooperateStatus);
            updateById(agentMainEnterpriseEntity);
        }

        return R.success("操作成功");
    }
}
