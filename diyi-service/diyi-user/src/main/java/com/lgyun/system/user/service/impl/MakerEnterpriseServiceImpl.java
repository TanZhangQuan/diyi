package com.lgyun.system.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lgyun.common.api.R;
import com.lgyun.common.enumeration.CooperateStatus;
import com.lgyun.common.enumeration.RelType;
import com.lgyun.core.mp.base.BaseServiceImpl;
import com.lgyun.system.user.entity.MakerEnterpriseEntity;
import com.lgyun.system.user.mapper.MakerEnterpriseMapper;
import com.lgyun.system.user.service.IMakerEnterpriseService;
import com.lgyun.system.user.vo.MakerEnterpriseRelationVO;
import com.lgyun.system.user.vo.RelEnterpriseMakerVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;

/**
 * Service 实现
 *
 * @author liangfeihu
 * @since 2020-06-26 17:21:05
 */
@Slf4j
@Service
@AllArgsConstructor
public class MakerEnterpriseServiceImpl extends BaseServiceImpl<MakerEnterpriseMapper, MakerEnterpriseEntity> implements IMakerEnterpriseService {

    @Override
    public void makerEnterpriseEntitySave(Long enterpriseId, Long makerId) {
        MakerEnterpriseEntity makerEnterpriseEntity = getEnterpriseIdAndMakerId(enterpriseId, makerId);
        if (makerEnterpriseEntity != null) {
            if (!(makerEnterpriseEntity.getRelationshipType() == 0 && CooperateStatus.COOPERATING.equals(makerEnterpriseEntity.getCooperateStatus()))) {
                makerEnterpriseEntity.setRelationshipType(0);
                makerEnterpriseEntity.setFirstCooperation(false);
                makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
                save(makerEnterpriseEntity);
            } else {
                return;
            }
        }

        makerEnterpriseEntity = new MakerEnterpriseEntity();
        makerEnterpriseEntity.setMakerId(makerId);
        makerEnterpriseEntity.setEnterpriseId(enterpriseId);
        makerEnterpriseEntity.setRelationshipType(0);
        makerEnterpriseEntity.setRelDate(new Date());
        makerEnterpriseEntity.setRelType(RelType.ENTERPRISEREL);
        makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATING);
        makerEnterpriseEntity.setCooperationStartTime(new Date());
        makerEnterpriseEntity.setFirstCooperation(true);
        makerEnterpriseEntity.setRelMemo("关联");
        save(makerEnterpriseEntity);
    }

    @Override
    public IPage<MakerEnterpriseRelationVO> selectMakerEnterprisePage(IPage<MakerEnterpriseRelationVO> page, Long makerId, Integer relationshipType) {
        return page.setRecords(baseMapper.selectMakerEnterprisePage(makerId, relationshipType, page));
    }

    @Override
    public R<String> addOrCancelfollow(Long enterpriseId, Long makerId, Integer attribute) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .eq(MakerEnterpriseEntity::getRelationshipType, 1);
        MakerEnterpriseEntity makerEnterpriseEntity = baseMapper.selectOne(queryWrapper);

        if (attribute == 1 && null == makerEnterpriseEntity) {
            return R.fail("取消成功1");
        }
        if (null == makerEnterpriseEntity) {
            makerEnterpriseEntity = new MakerEnterpriseEntity();
            makerEnterpriseEntity.setMakerId(makerId);
            makerEnterpriseEntity.setEnterpriseId(enterpriseId);
            makerEnterpriseEntity.setRelationshipType(1);
            makerEnterpriseEntity.setRelDate(new Date());
            makerEnterpriseEntity.setRelType(RelType.MAKERREL);
            makerEnterpriseEntity.setCooperationStartTime(new Date());
            makerEnterpriseEntity.setFirstCooperation(true);
            makerEnterpriseEntity.setRelMemo("关注");
        }
        boolean b = saveOrUpdate(makerEnterpriseEntity);
        System.out.println(b);

        return R.success("成功");
    }

    @Override
    public MakerEnterpriseEntity getEnterpriseIdAndMakerId(Long enterpriseId, Long makerId) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public MakerEnterpriseEntity getEnterpriseIdAndMakerIdAndRelationshipType(Long enterpriseId, Long makerId, Integer relationshipType) {
        QueryWrapper<MakerEnterpriseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(MakerEnterpriseEntity::getMakerId, makerId)
                .eq(MakerEnterpriseEntity::getEnterpriseId, enterpriseId)
                .eq(MakerEnterpriseEntity::getRelationshipType, relationshipType)
                .eq(MakerEnterpriseEntity::getCooperateStatus, CooperateStatus.COOPERATING);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public R<IPage<RelEnterpriseMakerVO>> getRelEnterpriseMaker(IPage<RelEnterpriseMakerVO> page, Long enterpriseId, Integer relationshipType, String keyword) {
        return R.data(page.setRecords(baseMapper.getRelEnterpriseMaker(enterpriseId, relationshipType, keyword,  page)));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> relMakers(Set<Long> makerIds, Long enterpriseId) {

        makerIds.forEach(makerId -> {
            try {
                //关联创客
                makerEnterpriseEntitySave(enterpriseId, makerId);
            } catch (Exception e) {
                log.error("关联创客异常", e);
            }
        });

        return R.success("关联成功");
    }

    @Override
    public R<String> cancelMakersRel(Set<Long> makerIds, Long enterpriseId) {

        makerIds.forEach(makerId -> {
            try {
                //取消创客关注
                MakerEnterpriseEntity makerEnterpriseEntity = getEnterpriseIdAndMakerId(enterpriseId, makerId);
                if (makerEnterpriseEntity != null) {
                    if (makerEnterpriseEntity.getRelationshipType() == 1) {
                        makerEnterpriseEntity.setRelationshipType(2);
                        save(makerEnterpriseEntity);
                    }
                }
            } catch (Exception e) {
                log.error("取消创客关注异常", e);
            }
        });

        return R.success("取消关注成功");
    }

    @Override
    public R<String> cancelRelMakers(Set<Long> makerIds, Long enterpriseId) {

        makerIds.forEach(makerId -> {
            try {
                //取消创客关联
                MakerEnterpriseEntity makerEnterpriseEntity = getEnterpriseIdAndMakerId(enterpriseId, makerId);
                if (makerEnterpriseEntity != null) {
                    if (makerEnterpriseEntity.getRelationshipType() == 0 && CooperateStatus.COOPERATING.equals(makerEnterpriseEntity.getCooperateStatus())) {
                        makerEnterpriseEntity.setRelationshipType(2);
                        makerEnterpriseEntity.setCooperateStatus(CooperateStatus.COOPERATESTOP);
                        save(makerEnterpriseEntity);
                    }
                }
            } catch (Exception e) {
                log.error("取消创客关联异常", e);
            }
        });

        return R.success("取消关联成功");
    }

}
