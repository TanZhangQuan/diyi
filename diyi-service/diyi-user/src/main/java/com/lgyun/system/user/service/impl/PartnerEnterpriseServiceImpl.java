package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.CooperateType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.EnterpriseEntity;
import com.lgyun.system.user.entity.PartnerEnterpriseEntity;
import com.lgyun.system.user.entity.PartnerEntity;
import com.lgyun.system.user.mapper.PartnerEnterpriseMapper;
import com.lgyun.system.user.service.IEnterpriseService;
import com.lgyun.system.user.service.IPartnerEnterpriseService;
import com.lgyun.system.user.service.IPartnerService;
import com.lgyun.system.user.vo.CooperationEnterprisesListVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 合伙人-商户关联表 Service 实现
 *
 * @author tzq
 * @since 2020-10-20 18:25:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PartnerEnterpriseServiceImpl extends BaseServiceImpl<PartnerEnterpriseMapper, PartnerEnterpriseEntity> implements IPartnerEnterpriseService {

    private final IPartnerService partnerService;

    @Autowired
    @Lazy
    private IEnterpriseService enterpriseService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relevancePartnerEnterprise(Long partnerId, Long enterpriseId, String matchDesc) {

        PartnerEntity partnerEntity = partnerService.getById(partnerId);
        if (partnerEntity == null) {
            return R.fail("合伙人不存在");
        }

        EnterpriseEntity enterpriseEntity = enterpriseService.getById(enterpriseId);
        if (enterpriseEntity == null) {
            return R.fail("商户不存在");
        }

        //判断商户是否已分配
        int partnerEnterpriseNum = queryPartnerEnterpriseCount(partnerId, enterpriseId);
        if (partnerEnterpriseNum > 0) {
            return R.fail("商户已分配合伙人");
        }

        PartnerEnterpriseEntity partnerEnterpriseEntity = queryByPartnerAndEnterprise(partnerId, enterpriseId);
        if (partnerEnterpriseEntity == null) {
            partnerEnterpriseEntity = new PartnerEnterpriseEntity();
            partnerEnterpriseEntity.setPartnerId(partnerId);
            partnerEnterpriseEntity.setEnterpriseId(enterpriseId);
            partnerEnterpriseEntity.setCooperateType(CooperateType.ALLOCATION);
            partnerEnterpriseEntity.setMatchDesc(matchDesc);
            save(partnerEnterpriseEntity);

        } else {
            if (!(CooperateStatus.COOPERATING.equals(partnerEnterpriseEntity.getCooperateStatus()))) {
                partnerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                updateById(partnerEnterpriseEntity);
            }
        }

        return R.success("匹配商户成功");
    }

    @Override
    public int queryPartnerEnterpriseCount(Long partnerId, Long enterpriseId) {
        QueryWrapper<PartnerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .ne(PartnerEnterpriseEntity::getPartnerId, partnerId);

        return baseMapper.selectCount(queryWrapper);
    }

    @Override
    public PartnerEnterpriseEntity queryByPartnerAndEnterprise(Long partnerId, Long enterpriseId) {
        QueryWrapper<PartnerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PartnerEnterpriseEntity::getPartnerId, partnerId)
                .eq(PartnerEnterpriseEntity::getEnterpriseId, enterpriseId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<CooperationEnterprisesListVO>> queryCooperationEnterpriseList(Long partnerId, String enterpriseName, IPage<CooperationEnterprisesListVO> page) {
        return R.data(page.setRecords(baseMapper.queryCooperationEnterpriseList(partnerId, enterpriseName, page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R updateCooperationStatus(Long partnerId, Long enterpriseId, CooperateStatus cooperateStatus) {
        int partnerNum = partnerService.queryCountById(partnerId);
        if (partnerNum <= 0) {
            return R.fail("合伙人不存在");
        }

        int enterpriseNum = enterpriseService.queryCountById(enterpriseId);
        if (enterpriseNum <= 0) {
            return R.fail("商户不存在");
        }

        PartnerEnterpriseEntity partnerEnterpriseEntity = queryByPartnerAndEnterprise(partnerId, enterpriseId);
        if (partnerEnterpriseEntity == null) {
            return R.fail("商户服务商不存在合作关系");
        }

        if (CooperateType.CREATE.equals(partnerEnterpriseEntity.getCooperateType())) {
            return R.fail("合伙人-商户为创建关系，不可更改");
        }

        if (!(partnerEnterpriseEntity.getCooperateStatus().equals(cooperateStatus))) {
            partnerEnterpriseEntity.setCooperateStatus(cooperateStatus);
            updateById(partnerEnterpriseEntity);
        }

        return R.success("操作成功");
    }
}
