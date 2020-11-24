package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelBureauType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.*;
import com.lgyun.system.user.mapper.RelBureauServiceProviderMapper;
import com.lgyun.system.user.service.IRelBureauService;
import com.lgyun.system.user.service.IRelBureauServiceProviderService;
import com.lgyun.system.user.service.IServiceProviderService;
import com.lgyun.system.user.vo.CooperationServiceProviderListVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 相关局与服务商关联表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:47:56
 */
@Slf4j
@Service
@AllArgsConstructor
public class RelBureauServiceProviderServiceImpl extends BaseServiceImpl<RelBureauServiceProviderMapper, RelBureauServiceProviderEntity> implements IRelBureauServiceProviderService {

    private IRelBureauService relBureauService;
    private IServiceProviderService serviceProviderService;

    @Override
    public R<String> relevanceRelBureauServiceProvider(Long relBureauId, Long serviceProviderId, String matchDesc, AdminEntity adminEntity) {
        RelBureauEntity relBureauEntity = relBureauService.getById(relBureauId);
        if (relBureauEntity == null) {
            return R.fail("相关局不存在");
        }

        ServiceProviderEntity serviceProviderEntity = serviceProviderService.getById(serviceProviderId);
        if (serviceProviderEntity == null) {
            return R.fail("服务商不存在");
        }

        RelBureauServiceProviderEntity relBureauServiceProviderEntity = queryByAgentMainAndServiceProvider(relBureauId, serviceProviderId);
        if (relBureauServiceProviderEntity == null) {

            int relBureauServiceProviderNum = queryRelBureauServiceProviderNum(relBureauEntity.getRelBureauType(), serviceProviderId);
            if (relBureauServiceProviderNum > 0) {
                return R.fail("相关局类型-服务商已存在");
            }

            relBureauServiceProviderEntity = new RelBureauServiceProviderEntity();
            relBureauServiceProviderEntity.setRelBureauId(relBureauId);
            relBureauServiceProviderEntity.setServiceProviderId(serviceProviderId);
            relBureauServiceProviderEntity.setRelBureauType(relBureauEntity.getRelBureauType());
            relBureauServiceProviderEntity.setMatchPerson(adminEntity.getName());
            relBureauServiceProviderEntity.setMatchDesc(matchDesc);
            save(relBureauServiceProviderEntity);
        } else {
            if (!(CooperateStatus.COOPERATING.equals(relBureauServiceProviderEntity.getCooperateStatus()))) {
                relBureauServiceProviderEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                updateById(relBureauServiceProviderEntity);
            }
        }

        return R.success("匹配服务商成功");
    }

    @Override
    public RelBureauServiceProviderEntity queryByAgentMainAndServiceProvider(Long relBureauId, Long serviceProviderId) {
        QueryWrapper<RelBureauServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauServiceProviderEntity::getRelBureauId, relBureauId).
                eq(RelBureauServiceProviderEntity::getServiceProviderId, serviceProviderId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int queryRelBureauServiceProviderNum(RelBureauType relBureauType, Long serviceProviderId) {
        QueryWrapper<RelBureauServiceProviderEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(RelBureauServiceProviderEntity::getRelBureauType, relBureauType).
                eq(RelBureauServiceProviderEntity::getServiceProviderId, serviceProviderId);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public R<IPage<CooperationServiceProviderListVO>> queryCooperationServiceProviderList(Long relBureauId, String serviceProviderName, IPage<CooperationServiceProviderListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationServiceProviderList(relBureauId, serviceProviderName, page)));
    }

    @Override
    public R<String> updateCooperationStatus(Long relBureauId, Long serviceProviderId, CooperateStatus cooperateStatus) {
        int relBureauNum = relBureauService.queryCountById(relBureauId);
        if (relBureauNum <= 0) {
            return R.fail("相关局不存在");
        }

        int serviceProviderNum = serviceProviderService.queryCountById(serviceProviderId);
        if (serviceProviderNum <= 0) {
            return R.fail("服务商不存在");
        }

        RelBureauServiceProviderEntity relBureauServiceProviderEntity = queryByAgentMainAndServiceProvider(relBureauId, serviceProviderId);
        if (relBureauServiceProviderEntity == null) {
            return R.fail("相关局-服务商不存在合作关系");
        }

        if (!(relBureauServiceProviderEntity.getCooperateStatus().equals(cooperateStatus))) {
            relBureauServiceProviderEntity.setCooperateStatus(cooperateStatus);
            updateById(relBureauServiceProviderEntity);
        }

        return R.success("操作成功");
    }

}
